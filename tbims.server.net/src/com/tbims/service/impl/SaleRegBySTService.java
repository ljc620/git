package com.tbims.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.bean.TicketTypeBeforePriceBean;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SlOrgSaleinfo;
import com.tbims.entity.SlOrgSaleinfoId;
import com.tbims.entity.SlPeriod;
import com.tbims.entity.SlSaleReg;
import com.tbims.entity.StrOutletInfo;
import com.tbims.entity.StrOutletInfoId;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.service.ISaleRegBySTService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class SaleRegBySTService extends BaseService implements ISaleRegBySTService {

	@Override
	public void saveSaleReg(UserBean userBean, SlSaleReg slSaleReg) throws ServiceException, DBException {
		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();

			SlSaleReg slSaleRegCheck = dbUtil.queryFirstForBean("检验重复", "SELECT * FROM SL_SALE_REG WHERE TICKET_TYPE_ID=? AND SALE_DATE=TRUNC(?,'DD')", SlSaleReg.class, slSaleReg.getTicketTypeId(),
					new Date());
			if (slSaleRegCheck != null) {
				throw new ServiceException(MSG.BF_ERROR, "当天的此票种已添加,不允许再添加");
			}

			long price = this.queryPrice(new Date(), slSaleReg.getTicketTypeId());

			// 计算销售金额
			// long saleAmt = price * CommonUtil.covertLong(slSaleReg.getSaleNum());
			Map<String, SlOrgSaleinfo> slOrgSaleinfoMap = dbUtil.queryMapForBean("查询机构售票信息表", "SELECT * FROM SL_ORG_SALEINFO WHERE ORG_ID=?", SlOrgSaleinfo.class, "id.ticketTypeId",
					userBean.getOrgId());
			SlOrgSaleinfo slOrgSaleinfo = slOrgSaleinfoMap.get(slSaleReg.getTicketTypeId());
			if (slOrgSaleinfo == null) {
				slOrgSaleinfo = new SlOrgSaleinfo();
				slOrgSaleinfo.setId(new SlOrgSaleinfoId(userBean.getOrgId(), slSaleReg.getTicketTypeId()));
				slOrgSaleinfo.setOpeTime(new Date());
				slOrgSaleinfo.setSaleTotalNum(0L);
				slOrgSaleinfoMap.put(slSaleReg.getTicketTypeId(), slOrgSaleinfo);
			}
			long saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();
			slOrgSaleinfo.setSaleTotalNum(saleTotalNumOld + slSaleReg.getSaleNum());

//			TicketTypeBeforePriceBean ticketTypeBeforePriceBean = this.calcTicketTypeByOrg(slSaleReg.getTicketTypeId(), saleTotalNumOld, price, CommonUtil.covertLong(slSaleReg.getSaleNum()));
			long minusAmt = this.calcTicketTypeByOrg(slSaleReg.getTicketTypeId(), saleTotalNumOld, price, CommonUtil.covertLong(slSaleReg.getSaleNum()));

			slSaleReg.setSaleAmt(minusAmt);
			slSaleReg.setSaleRegId(UUIDGenerator.getPK());
			slSaleReg.setSaleDate(new Date());
			slSaleReg.setOrgId(userBean.getOrgId());
			slSaleReg.setOutletId(userBean.getOutletId());

			// 扣减预付款
			SlOrg slOrg = dbUtil.findById("查询机构信息", SlOrg.class, userBean.getOrgId());
			slOrg.setAdvanceAmt(CommonUtil.covertLong(slOrg.getAdvanceAmt()) - minusAmt);

			// 更新库存
			checkOutletInfos(userBean.getOutletId(), slSaleReg.getTicketTypeId());
			reduceOutletInfos(session, userBean.getOutletId(), slSaleReg.getTicketTypeId(), CommonUtil.covertLong(slSaleReg.getSaleNum()));

			dbUtil.saveEntity("保存销售信息", session, slSaleReg);
			dbUtil.saveOrUpdateEntity("更新销售张数", session, slOrgSaleinfo);
			dbUtil.updateEntity("扣减预付款", session, slOrg);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	/**
	 * Title: 减少网点库存<br/>
	 * Description:
	 * 
	 * @param session
	 * @param outletId
	 * @param ticketTypeId
	 * @param ticketNum
	 */
	private void reduceOutletInfos(Session session, Long outletId, String ticketTypeId, Long ticketNum) {
		dbUtil.executeSql("更新网点库存", session, "UPDATE STR_OUTLET_INFO SET STR_TICKET_NUM=STR_TICKET_NUM-?,last_upd_time=sysdate WHERE OUTLET_ID=? AND TICKET_TYPE_ID=?", ticketNum, outletId,
				ticketTypeId);
	}

	private void checkOutletInfos(Long outletId, String ticketTypeId) {
		if (outletId == null || outletId == 0) {
			return;
		}

		List<SysTicketType> sysTicketTypeList = dbUtil.queryListToBean("", "SELECT * FROM SYS_TICKET_TYPE WHERE TICKET_TYPE_ID=?", SysTicketType.class, ticketTypeId);
		for (SysTicketType sysTicketType : sysTicketTypeList) {
			StrOutletInfo strOutletInfo = dbUtil.findById("", StrOutletInfo.class, new StrOutletInfoId(outletId, sysTicketType.getTicketTypeId()));
			if (strOutletInfo == null) {
				StrOutletInfo strOutletInfoNew = new StrOutletInfo();
				strOutletInfoNew.setId(new StrOutletInfoId(outletId, sysTicketType.getTicketTypeId()));
				strOutletInfoNew.setStrTicketNum(0L); // 核减网点库存数量增加
				strOutletInfoNew.setLastUpdTime(new Date());
				dbUtil.saveEntity("添加网点库存", strOutletInfoNew);
			}
		}
	}

	/**
	 * Title:获取票价 <br/>
	 * Description:
	 * 
	 * @param date
	 * @param ticketTypeId
	 * @return
	 */
	private long queryPrice(Date date, String ticketTypeId) {
		SlPeriod slPeriod = dbUtil.queryFirstForBean("查询预售期票价", "SELECT * FROM SL_PERIOD WHERE TICKET_TYPE_ID=? AND TRUNC(?,'DD') BETWEEN BEGIN_DT AND END_DT ", SlPeriod.class, ticketTypeId, date);
		if (slPeriod != null) {
			return CommonUtil.covertLong(slPeriod.getDiscountPrice());
		}

		SysTicketType sysTicketType = dbUtil.queryFirstForBean("查询票种", "SELECT * FROM SYS_TICKET_TYPE WHERE TICKET_TYPE_ID=?", SysTicketType.class, ticketTypeId);
		return sysTicketType.getPrice();
	}

	/**
	 * Title:计算签约社、代理商需要扣除的预付款 <br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param saleTotalNumOld 原机构销售张数
	 * @param ticketTypePrice 基准票价
	 * @param minusNum 当前销售张数
	 * @return
	 */
	private long calcTicketTypeByOrg(String ticketTypeId, long saleTotalNumOld, long ticketTypePrice, long minusNum) {
		// 逐个阶梯循环计算阶梯总票款，如果超过阶梯最大值，则按基准票加计算票款  20180601  zhuxy
		long minusAmt = 0;
		long tempNum = minusNum;
		long lastTotalNum = saleTotalNumOld;
		List<SysTicketTypePrice> sysTicketTypePriceList = dbUtil.queryListToBean("查询阶梯票价1", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND END_NO>? order by END_NO ASC",
				SysTicketTypePrice.class, ticketTypeId, saleTotalNumOld);
		for(SysTicketTypePrice sysTicketTypePrice : sysTicketTypePriceList) {
			if(tempNum == 0)
				break;
			long lvNum;
			if(lastTotalNum + tempNum <= sysTicketTypePrice.getEndNo()) {
				lvNum = tempNum;
			} else {
				lvNum = sysTicketTypePrice.getEndNo() - lastTotalNum;
			}
			tempNum -= lvNum;
			lastTotalNum += lvNum;
			minusAmt += lvNum * sysTicketTypePrice.getPrice();
		}
		if(tempNum > 0){
			minusAmt += tempNum * ticketTypePrice;
		}
		return minusAmt;
		
		
		// 旧的计算方法，只能跨2个阶梯，否则会造成实体代理登记时扣除的预付款错误 20180601 zhuxy
		
//		TicketTypeBeforePriceBean ticketTypeBeforePriceBean = new TicketTypeBeforePriceBean();
//		long saleTotalNumNew = saleTotalNumOld + minusNum;// 新销售总张数
//
//		long priceOld = 0;// 原票价
//		long minusDllOld = 0;// 原待扣除张数
//		SysTicketTypePrice sysTicketTypePriceOld = dbUtil.queryFirstForBean("查询阶梯票价1", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)",
//				SysTicketTypePrice.class, ticketTypeId, saleTotalNumOld);
//		if (sysTicketTypePriceOld != null) {
//			minusDllOld = CommonUtil.covertLong(sysTicketTypePriceOld.getEndNo()) - saleTotalNumOld;
//			priceOld = CommonUtil.covertLong(sysTicketTypePriceOld.getPrice());
//		}
//
//		long priceNew = 0;// 新票价
//		long minusDllNew = 0;// 新待扣除张数
//		SysTicketTypePrice sysTicketTypePriceNew = dbUtil.queryFirstForBean("查询阶梯票价2", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)",
//				SysTicketTypePrice.class, ticketTypeId, saleTotalNumNew);
//		if (sysTicketTypePriceNew != null) {
//			minusDllNew = saleTotalNumNew - CommonUtil.covertLong(sysTicketTypePriceNew.getStartNo()) + 1;
//			priceNew = CommonUtil.covertLong(sysTicketTypePriceNew.getPrice());
//		}
//
//		// 无阶梯票价，按票种基准价格
//		if (sysTicketTypePriceOld == null && sysTicketTypePriceNew == null) {
//			ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
//			ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
//			ticketTypeBeforePriceBean.setMinusAmt(ticketTypePrice * minusNum);
//			return ticketTypeBeforePriceBean;
//		}
//
//		// 原销售张数和新销售张数在同一阶梯票价中
//		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew != null) {
//			if (sysTicketTypePriceOld.getPriceId().equals(sysTicketTypePriceNew.getPriceId())) {
//				ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
//				ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
//				ticketTypeBeforePriceBean.setMinusAmt(CommonUtil.covertLong(sysTicketTypePriceNew.getPrice()) * minusNum);
//				ticketTypeBeforePriceBean.setLv1Price(sysTicketTypePriceNew.getPrice());
//				ticketTypeBeforePriceBean.setLv1Num(minusNum);
//				return ticketTypeBeforePriceBean;
//			}
//		}
//
//		// 原销售张数没有阶梯票价和新销售张数有阶梯票，处理
//		if (sysTicketTypePriceOld == null && sysTicketTypePriceNew != null) {
//			minusDllOld = minusNum - minusDllNew;
//			priceOld = ticketTypePrice;
//		}
//
//		// 原销售张数有阶梯票价和新销售张数没有阶梯票，处理
//		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew == null) {
//			minusDllNew = minusNum - minusDllOld;
//			priceNew = ticketTypePrice;
//		}
//
//		ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
//		ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
//		ticketTypeBeforePriceBean.setMinusAmt(minusDllOld * priceOld + minusDllNew * priceNew);
//		ticketTypeBeforePriceBean.setLv1Price(priceOld);
//		ticketTypeBeforePriceBean.setLv1Num(minusDllOld);
//		ticketTypeBeforePriceBean.setLv2Price(priceNew);
//		ticketTypeBeforePriceBean.setLv2Num(minusDllNew);
//		return ticketTypeBeforePriceBean;
	}

	@Override
	public void updateSaleReg(UserBean userBean, SlSaleReg slSaleReg) throws ServiceException, DBException {
		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();

			SlSaleReg slSaleRegOld = dbUtil.findById("查询销售记录", SlSaleReg.class, slSaleReg.getSaleRegId());
			if (!DateUtils.truncatedEquals(new Date(), slSaleRegOld.getSaleDate(), Calendar.DATE)) {
				throw new ServiceException(MSG.BF_ERROR, "只允许修改当天销售记录");
			}

			long saleNumOld = slSaleRegOld.getSaleNum();// 原销售数量
			long saleAmtOld = slSaleRegOld.getSaleAmt();// 原销售金额
			long saleNumNew = CommonUtil.covertLong(slSaleReg.getSaleNum());// 新销售数量

			long price = this.queryPrice(new Date(), slSaleReg.getTicketTypeId());
			// long saleAmt = price * saleNumNew;// 新销售金额

			// 计算销售金额
			// long saleAmt = price * CommonUtil.covertLong(slSaleReg.getSaleNum());
			Map<String, SlOrgSaleinfo> slOrgSaleinfoMap = dbUtil.queryMapForBean("查询机构售票信息表", "SELECT * FROM SL_ORG_SALEINFO WHERE ORG_ID=?", SlOrgSaleinfo.class, "id.ticketTypeId",
					userBean.getOrgId());
			SlOrgSaleinfo slOrgSaleinfo = slOrgSaleinfoMap.get(slSaleReg.getTicketTypeId());
			if (slOrgSaleinfo == null) {
				slOrgSaleinfo = new SlOrgSaleinfo();
				slOrgSaleinfo.setId(new SlOrgSaleinfoId(userBean.getOrgId(), slSaleReg.getTicketTypeId()));
				slOrgSaleinfo.setOpeTime(new Date());
				slOrgSaleinfo.setSaleTotalNum(0L);
				slOrgSaleinfoMap.put(slSaleReg.getTicketTypeId(), slOrgSaleinfo);
			}
			long saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();
			slOrgSaleinfo.setSaleTotalNum(saleTotalNumOld - saleNumOld + slSaleReg.getSaleNum());

			// 新销售金额
//			TicketTypeBeforePriceBean ticketTypeBeforePriceBean = this.calcTicketTypeByOrg(slSaleReg.getTicketTypeId(), saleTotalNumOld, price, CommonUtil.covertLong(slSaleReg.getSaleNum()));
			long minusAmt = this.calcTicketTypeByOrg(slSaleReg.getTicketTypeId(), saleTotalNumOld, price, CommonUtil.covertLong(slSaleReg.getSaleNum()));

			slSaleRegOld.setSaleNum(saleNumNew);
			slSaleRegOld.setSaleAmt(minusAmt);

			// 扣减预付款
			SlOrg slOrg = dbUtil.findById("查询机构信息", SlOrg.class, userBean.getOrgId());
			slOrg.setAdvanceAmt(CommonUtil.covertLong(slOrg.getAdvanceAmt()) + saleAmtOld - minusAmt);

			// 更新库存
			checkOutletInfos(userBean.getOutletId(), slSaleReg.getTicketTypeId());
			reduceOutletInfos(session, userBean.getOutletId(), slSaleReg.getTicketTypeId(), saleNumNew - saleNumOld);

			dbUtil.updateEntity("保存销售信息", session, slSaleRegOld);
			dbUtil.saveOrUpdateEntity("更新销售张数", session, slOrgSaleinfo);
			dbUtil.updateEntity("扣减预付款", session, slOrg);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public PageBean<Map<String, Object>> listSaleReg(UserBean userBean, Date startDt, Date endDt, String ticketTypeId) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT A.SALE_REG_ID,TO_CHAR(A.SALE_DATE,'YYYY-MM-DD') SALE_DATE, B.TICKET_TYPE_ID,B.TICKET_TYPE_NAME ,A.SALE_NUM,A.SALE_AMT      ");
		sql.append("FROM SL_SALE_REG A, SYS_TICKET_TYPE B  WHERE A.TICKET_TYPE_ID = B.TICKET_TYPE_ID  ");
		sql.append(" AND A.ORG_ID=:ORG_ID");
		params.put("ORG_ID", userBean.getOrgId());

		if (startDt != null && endDt != null) {
			sql.append(" AND A.SALE_DATE BETWEEN TRUNC(:STARTDT,'DD') AND TRUNC(:ENDDT,'DD')");
			params.put("STARTDT", startDt);
			params.put("ENDDT", endDt);
		}

		if (StringUtil.isNotNull(ticketTypeId)) {
			sql.append(" AND A.TICKET_TYPE_ID=:TICKET_TYPE_ID");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}

		sql.append(" order by A.SALE_DATE desc,A.TICKET_TYPE_ID");

		PageBean<Map<String, Object>> slSaleRegList = dbUtil.queryPageToMap("查询销售信息", sql.toString(), userBean.getPageNum(), userBean.getPageSize(), params);

		return slSaleRegList;
	}

	public SlSaleReg getSlSaleRegById(String saleRegId) {
		return dbUtil.findById("查询销售记录", SlSaleReg.class, saleRegId);
	}

	@Override
	public List<SysTicketType> listTicketTypeByST(UserBean userBean) {
		String sql = "SELECT st.* FROM SYS_TICKET_TYPE st,SL_ORG_TICKETTYPE sot WHERE st.ticket_type_id=sot.ticket_type_id and st.TEAM_FLAG!='Y' and sot.org_id=? order by st.ticket_type_id";
		return dbUtil.queryListToBean("查询票种信息", sql, SysTicketType.class, userBean.getOrgId());
	}

}
