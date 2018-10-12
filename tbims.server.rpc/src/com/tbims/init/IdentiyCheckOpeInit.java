package com.tbims.init;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tbims.bean.IdentiyCheckCacheBean;
import com.tbims.cache.IdentiyCheckCache;
import com.tbims.db.entity.SlNetagentOrder;
import com.tbims.db.entity.SlOrder;
import com.tbims.db.entity.SlOrderDetail;
import com.tbims.db.entity.SlOrg;
import com.tbims.db.entity.SlOrgSaleinfo;
import com.tbims.db.entity.SlOrgSaleinfoId;
import com.tbims.db.entity.SlPayType;
import com.tbims.db.entity.SysTicketType;
import com.tbims.db.entity.SysTicketTypePrice;
import com.tbims.db.util.DBUtil;
import com.tbims.org.impl.OrgInterfaceUtilImpl;
import com.tbims.rpc.service.impl.SaleServiceImpl;
import com.tbims.util.CommonUtil;
import com.tbims.util.StringUtil;

/**
 * Title: 身份证检票后，机构的相关数据处理 <br/>
 * Description:
 * 
 * @ClassName: IdentiyCheckOpe
 * @author ydc
 * @date 2017年11月8日 上午10:19:33
 * 
 */
@Component
public class IdentiyCheckOpeInit {
	private static final Log logger = LogFactory.getLog(SaleServiceImpl.class);

	@Autowired
	private DBUtil dbUtil;

	@Autowired
	private OrgInterfaceUtilImpl orgInterfaceUtilImpl;

	/**
	 * Title:服务启动时,获取所有已检票未回调的订单明细进行处理 <br/>
	 * Description: fixedDelay = 10 * 60 * 1000 : 10分钟
	 */
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	private void identiyCheckByOrgDB() {
		try {
			// ORG_CALLBACK_STAT 机构回调状态(Y:已回调 N:未回调 S:跳过) CHECK_FLAG 是否检票(Y是N否)
			List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE CHECK_FLAG='Y' AND TICKET_CLASS='2' AND (ORG_CALLBACK_STAT='N' or ORG_CALLBACK_STAT='W')", SlOrderDetail.class);
			for (SlOrderDetail slOrderDetail : slOrderDetailList) {
				IdentiyCheckCacheBean identiyCheckCacheBean = new IdentiyCheckCacheBean();
				identiyCheckCacheBean.setIdentiy(slOrderDetail.getIdenttyId());
				identiyCheckCacheBean.setOrderId(slOrderDetail.getOrderId());
				identiyCheckCacheBean.setOrderDetailId(slOrderDetail.getOrderDetailId());
				identiyCheckCacheBean.setCheckTime(slOrderDetail.getCheckTime());
				identiyCheckById(identiyCheckCacheBean);
			}
		} catch (Exception e) {
			logger.error("定时回调失败", e);
		}
	}

	/**
	 * Title:开启循环获取缓存队列中的数据进行处理 <br/>
	 * Description:
	 */
	public void identiyCheckByOrgCacheRun() {
		Thread runThread = new Thread(new Runnable() {
			@Override
			public void run() {
				identiyCheckByOrgCache();
			}
		});

		runThread.start();
	}

	/**
	 * Title:循环获取缓存队列中的数据进行处理 <br/>
	 * Description:
	 */
	private void identiyCheckByOrgCache() {
		while (true) {
			try {
				IdentiyCheckCacheBean identiyCheckCacheBean = IdentiyCheckCache.getIdentiyCheckCacheDeque().take();
				identiyCheckById(identiyCheckCacheBean);
			} catch (Exception e) {
				logger.error("回调失败-队列错误", e);
			}
		}
	}

	/**
	 * Title:某一笔检票明细处理 <br/>
	 * Description: 方法同步，用于identiyCheckByOrgDB 和 identiyCheckByOrgCache 同步
	 * 
	 * @param identiyCheckCacheBean
	 */
	private synchronized void identiyCheckById(IdentiyCheckCacheBean identiyCheckCacheBean) {
		Session session = null;
		String orgId = null;
		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();
			// 解冻、扣除预付款，增加销售数量
			SlOrderDetail slOrderDetail = dbUtil.findById("", SlOrderDetail.class, identiyCheckCacheBean.getOrderDetailId());
			if (!slOrderDetail.getCheckFlag().equals("Y") || 
					(!slOrderDetail.getOrgCallbackStat().equals("N") && !slOrderDetail.getOrgCallbackStat().equals("W"))) {
				return;
			}

			SlNetagentOrder slNetagentOrder = dbUtil.findById("", SlNetagentOrder.class, identiyCheckCacheBean.getOrderId());
			if (slNetagentOrder == null) {	// 不是网络代理销售的设置为跳过
				// 机构回调状态(Y:已回调 N:未回调（未做内部核销） W:待网络核销回调（内部核销已处理） S:跳过).
				slOrderDetail.setOrgCallbackStat("S");
				dbUtil.updateEntity("", session, slOrderDetail);
				dbUtil.commit(session);
				return;
			}
			orgId = slNetagentOrder.getOrgId();
			// 机构加锁与网络人工换票同步
			SlOrg slOrg = dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, slNetagentOrder.getOrgId());
			if (slOrg == null) {
				// 机构回调状态(Y:已回调 N:未回调  W:待网络核销回调（内部核销已处理）S:跳过).
				slOrderDetail.setOrgCallbackStat("S");
				dbUtil.updateEntity("", session, slOrderDetail);
				dbUtil.commit(session);
				return;
			}

			// 内部核销未处理，先处理内部核销并，修改核销状态为W：待网络核销（内部核销已处理）  20180308 zhuxy
			// 不需回调的只做内部核销，并置核销状态为S：跳过回调
			if(slOrderDetail.getOrgCallbackStat().equals("N")) {
				// 增加销售张数
				SlOrgSaleinfo slOrgSaleinfo = dbUtil.findById("查询销售张数", SlOrgSaleinfo.class, new SlOrgSaleinfoId(slOrg.getOrgId(), slOrderDetail.getTicketTypeId()));
				long saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();
				addOrgSaleNum(session, slOrg.getOrgId(), slOrderDetail.getTicketTypeId(), (long) 1);
	
				// 获取票价
				Long price = getPrice(slOrderDetail.getTicketTypeId());
				Long newMinusAdvanceAmt = calcTicketTypeByOrg(slOrderDetail.getTicketTypeId(), saleTotalNumOld, price, (long) 1);
				Long minusAdvanceAmt = slOrderDetail.getSalePrice() - newMinusAdvanceAmt;
	
				// 解冻预付款
				minusfrozeOrgAdvanceAmt(session, slOrg.getOrgId(), slOrderDetail.getSalePrice());
				// 扣减预付款
				minusOrgAdvanceAmt(session, slOrg.getOrgId(), minusAdvanceAmt);
	
				slOrderDetail.setSalePrice(newMinusAdvanceAmt);
				// slOrderDetail.setOriginalPrice(newMinusAdvanceAmt);
	
				SlOrder slOrder = dbUtil.findById("", SlOrder.class, identiyCheckCacheBean.getOrderId());
				SlPayType slPayType = dbUtil.queryFirstForBean("", "SELECT * FROM SL_PAY_TYPE WHERE ORDER_ID=?", SlPayType.class, identiyCheckCacheBean.getOrderId());
	
				slOrder.setDueSum(slOrder.getDueSum() - minusAdvanceAmt);
				slOrder.setRealSum(slOrder.getRealSum() - minusAdvanceAmt);
	
				slPayType.setAmt(slPayType.getAmt() - minusAdvanceAmt);
	
				if(StringUtil.isNull(slOrg.getCallbackUrl())) // 如果回调地址为空，则跳过回调
					slOrderDetail.setOrgCallbackStat("S");
				else
					slOrderDetail.setOrgCallbackStat("W");// 机构回调状态(Y:已回调 N:未回调  W:待网络核销回调（内部核销已处理） S:跳过).
				dbUtil.updateEntity("内部核销", session, slOrderDetail);
				dbUtil.updateEntity("内部核销", session, slOrder);
				dbUtil.updateEntity("内部核销", session, slPayType);
				dbUtil.commit(session);
				
				if(StringUtil.isNull(slOrg.getCallbackUrl())) // 如果回调地址为空，内部核销后直接返回
					return;
			}
			// 开启事务
			session = dbUtil.getSessionByTransaction();
			// 第三方机构的售票回调接口
			orgInterfaceUtilImpl.checkTicketCallback(slOrg, identiyCheckCacheBean);

			slOrderDetail.setOrgCallbackStat("Y");// 机构回调状态(Y:已回调 N:未回调  W:待网络核销回调（内部核销已处理） S:跳过).
			slOrderDetail.setOrgCallbackTime(new Date());
			dbUtil.updateEntity("机构核销回调成功", session, slOrderDetail);

			dbUtil.commit(session);
		} catch (Exception e) {
			MDC.clear();
			logger.error("机构检票核销回调失败", e);
			dbUtil.rollback(session);
			if (StringUtil.isNotNull(orgId)) {
				dbUtil.executeSql("更新机构核销回调错误计数", "UPDATE SL_ORG SET CALLBACK_ERROR_COUNT=NVL(CALLBACK_ERROR_COUNT,0)+1 WHERE ORG_ID=?", orgId);
			}
		} finally {
			dbUtil.close(session);
		}

	}

	/**
	 * Title: 增加机构销售张数<br/>
	 * Description:
	 * 
	 */
	private void addOrgSaleNum(Session session, String orgId, String ticketTypeId, Long saleNum) {
		dbUtil.executeSql("更新机构销售数量", session, "UPDATE SL_ORG_SALEINFO SET SALE_TOTAL_NUM=SALE_TOTAL_NUM+?,OPE_TIME=sysdate WHERE ORG_ID=? AND TICKET_TYPE_ID=?", saleNum, orgId, ticketTypeId);
	}

	/**
	 * Title:扣减机构预付款 <br/>
	 * Description:
	 * 
	 */
	private void minusOrgAdvanceAmt(Session session, String orgId, Long saleAmt) {
		dbUtil.executeSql("扣减机构预付款", session, "UPDATE SL_ORG SET ADVANCE_AMT=ADVANCE_AMT+? WHERE ORG_ID=? ", saleAmt, orgId);
	}

	/**
	 * Title:冻结预付款减少<br/>
	 * Description:
	 * 
	 */
	private void minusfrozeOrgAdvanceAmt(Session session, String orgId, Long forzeAmt) {
		dbUtil.executeSql("减少冻结预付款", session, "UPDATE SL_ORG SET ADVANCE_FROZE_AMT=ADVANCE_FROZE_AMT-? WHERE ORG_ID=? ", forzeAmt, orgId);
	}

	/**
	 * Title:计算签约社需要扣除的预付款 <br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param saleTotalNumOld
	 * @param ticketTypePrice
	 * @param minusNum 去除额度后的销售张数
	 * @return
	 */
	private long calcTicketTypeByOrg(String ticketTypeId, long saleTotalNumOld, long ticketTypePrice, long minusNum) {
		long saleTotalNumNew = saleTotalNumOld + minusNum;// 新销售总张数

		long priceOld = 0;// 原票价
		long minusDllOld = 0;// 原待扣除张数
		SysTicketTypePrice sysTicketTypePriceOld = dbUtil.queryFirstForBean("查询阶梯票价1", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)", SysTicketTypePrice.class, ticketTypeId, saleTotalNumOld);
		if (sysTicketTypePriceOld != null) {
			minusDllOld = CommonUtil.covertLong(sysTicketTypePriceOld.getEndNo()) - saleTotalNumOld;
			priceOld = CommonUtil.covertLong(sysTicketTypePriceOld.getPrice());
		}

		long priceNew = 0;// 新票价
		long minusDllNew = 0;// 新待扣除张数
		SysTicketTypePrice sysTicketTypePriceNew = dbUtil.queryFirstForBean("查询阶梯票价2", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)", SysTicketTypePrice.class, ticketTypeId, saleTotalNumNew);
		if (sysTicketTypePriceNew != null) {
			minusDllNew = saleTotalNumNew - CommonUtil.covertLong(sysTicketTypePriceNew.getStartNo()) + 1;
			priceNew = CommonUtil.covertLong(sysTicketTypePriceNew.getPrice());
		}

		// 无阶梯票价，按票种基准价格
		if (sysTicketTypePriceOld == null && sysTicketTypePriceNew == null) {
			return ticketTypePrice * minusNum;
		}

		// 原销售张数和新销售张数在同一阶梯票价中
		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew != null) {
			if (sysTicketTypePriceOld.getPriceId().equals(sysTicketTypePriceNew.getPriceId())) {
				return CommonUtil.covertLong(sysTicketTypePriceNew.getPrice()) * minusNum;
			}
		}

		// 原销售张数没有阶梯票价和新销售张数有阶梯票，处理
		if (sysTicketTypePriceOld == null && sysTicketTypePriceNew != null) {
			minusDllOld = minusNum - minusDllNew;
			priceOld = ticketTypePrice;
		}

		// 原销售张数有阶梯票价和新销售张数没有阶梯票，处理
		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew == null) {
			minusDllNew = minusNum - minusDllOld;
			priceNew = ticketTypePrice;
		}

		return minusDllOld * priceOld + minusDllNew * priceNew;
	}

	/**
	 * Title: 获取单价<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @return
	 */
	private Long getPrice(String ticketTypeId) {
		SysTicketType sysTicketType = dbUtil.findById("", SysTicketType.class, ticketTypeId);
		// 获取票价
		Long price = sysTicketType.getPrice();
		return price;
	}
}
