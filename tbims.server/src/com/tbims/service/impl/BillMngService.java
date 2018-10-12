package com.tbims.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tbims.bean.RepairSaleOrderBean;
import com.tbims.bean.TicketTypeBeforePriceBean;
import com.tbims.entity.SlBill;
import com.tbims.entity.SlBillDetail;
import com.tbims.entity.SlNetagentOrder;
import com.tbims.entity.SlOrder;
import com.tbims.entity.SlOrderDetail;
import com.tbims.entity.SlOrderTickettypeDetail;
import com.tbims.entity.SlOrderTickettypeDetailId;
import com.tbims.entity.SlOrderVenueDetail;
import com.tbims.entity.SlOrderVenueDetailId;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SlOrgSaleinfo;
import com.tbims.entity.SlOrgSaleinfoId;
import com.tbims.entity.SlPayType;
import com.tbims.entity.SlRefundTicket;
import com.tbims.entity.SlRefundTicketDetail;
import com.tbims.entity.StrTicketInfo;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.entity.SysTicketTypeVenue;
import com.tbims.pay.PayUtil;
import com.tbims.pay.bean.BillOrderRequest;
import com.tbims.pay.bean.RefundOrderRequest;
import com.tbims.pay.bfbank.BfConstant;
import com.tbims.pay.bfbank.config.SwiftpassConfig;
import com.tbims.service.IBillMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class BillMngService extends BaseService implements IBillMngService {
	private static final Log logger = LogFactory.getLog(BillMngService.class);

	@Autowired
	@Qualifier("payUtilByBfBank")
	PayUtil payUtil;

	@Override
	public PageBean<SlBill> listBillInfo(UserBean userBean, String billDateStart, String billDateEnd, String stat) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT * FROM SL_BILL WHERE 1=1");
		if (StringUtil.isNotNull(billDateStart) && StringUtil.isNotNull(billDateEnd)) {
			sql.append(" AND BILL_DATE between :BILLDATESTART and :BILLDATEEND ");
			params.put("BILLDATESTART", billDateStart);
			params.put("BILLDATEEND", billDateEnd);
		}

		if (StringUtil.isNotNull(stat) && stat.equals("0")) {
			sql.append(" AND STAT =:STAT ");
			params.put("STAT", stat);
		}

		if (StringUtil.isNotNull(stat) && stat.equals("1")) {
			sql.append(" AND STAT <>'0' ");
		}

		sql.append(" ORDER BY BILL_DATE DESC ");

		PageBean<SlBill> slBillList = dbUtil.queryPageToBean("查询对账列表", sql.toString(), SlBill.class, userBean.getPageNum(), userBean.getPageSize(), params);

		return slBillList;
	}

	@Override
	public PageBean<SlBillDetail> listBillDetail(UserBean userBean, String billId, String tranStatus, String billResult) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT * FROM SL_BILL_DETAIL WHERE BILL_ID=:BILL_ID ");
		params.put("BILL_ID", billId);

		if (StringUtil.isNotNull(tranStatus)) {
			sql.append(" AND TRAN_STATUS=:TRAN_STATUS");
			params.put("TRAN_STATUS", tranStatus);
		}

		if (StringUtil.isNotNull(billResult)) {
			sql.append(" AND BILL_RESULT=:BILL_RESULT");
			params.put("BILL_RESULT", billResult);
		}

		sql.append(" ORDER BY TRAN_TIME DESC ");

		PageBean<SlBillDetail> slBillDetailList = dbUtil.queryPageToBean("查询对账明细", sql.toString(), SlBillDetail.class, userBean.getPageNum(), userBean.getPageSize(), params);

		return slBillDetailList;
	}

	@Override
	public void redoBill(UserBean userBean, String billId) throws ServiceException {
		SlBill slBill = dbUtil.findById("查询对账信息", SlBill.class, billId);
		if (slBill.getStat().equals("0")) {
			throw new ServiceException(MSG.ERROR, "对账成功,不允许再对账");
		}
		BillOrderRequest billOrderBean = new BillOrderRequest();
		billOrderBean.setBill_date(slBill.getBillDate());
		billOrderBean.setMch_id(SwiftpassConfig.mch_id);
		billOrderBean.setBill_type(BfConstant.BILL_TYPE);
		payUtil.billOrder(billOrderBean);
	}

	@Override
	public void refundPay(UserBean userBean, String billDetailId) throws ServiceException {
		SlBillDetail slBillDetail = dbUtil.findById("查询对账明细", SlBillDetail.class, billDetailId);
		SlOrder slOrder = dbUtil.findById("查询订单", SlOrder.class, slBillDetail.getOutTradeNo());
		List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("查询订单明细", "SELECT * FROM SL_ORDER_DETAIL where ORDER_ID=?", SlOrderDetail.class, slBillDetail.getOutTradeNo());

		RefundOrderRequest tradeOrderRequest = new RefundOrderRequest();
		tradeOrderRequest.setOp_user_id(SwiftpassConfig.mch_id);
		tradeOrderRequest.setMch_id(SwiftpassConfig.mch_id);
		tradeOrderRequest.setOut_refund_no(slOrder.getOrderId() + "-R01");// R01:对账失败全额退款
																			// R02:手动退款(未取票的售票记录)
		tradeOrderRequest.setOut_trade_no(slOrder.getOrderId());
		tradeOrderRequest.setTotal_fee(CommonUtil.covertInt(slOrder.getRealSum()));
		tradeOrderRequest.setRefund_fee(CommonUtil.covertInt(slOrder.getRealSum()));

		payUtil.refundOrderPay(tradeOrderRequest);

		Session session = null;
		SlRefundTicket slRefundTicket = new SlRefundTicket();
		List<SlRefundTicketDetail> slRefundTicketDetailList = new ArrayList<SlRefundTicketDetail>();
		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 保存退款信息
			slRefundTicket.setRefundId(tradeOrderRequest.getOut_refund_no());
			slRefundTicket.setOrderType(slOrder.getOrderType());
			slRefundTicket.setOrgId(tradeOrderRequest.getOp_user_id());
			slRefundTicket.setOrderId(slOrder.getOrderId());
			slRefundTicket.setTicketCount(slOrder.getTicketCount());
			slRefundTicket.setRefundAmtSum(slOrder.getRealSum());
			slRefundTicket.setRefundTime(new Date());

			for (SlOrderDetail slOrderDetail : slOrderDetailList) {
				SlRefundTicketDetail slRefundTicketDetail = new SlRefundTicketDetail();
				slRefundTicketDetail.setRefundDetailId(UUIDGenerator.getPK());
				slRefundTicketDetail.setRefundId(slRefundTicket.getRefundId());
				slRefundTicketDetail.setTicketClass(slOrderDetail.getTicketClass());
				slRefundTicketDetail.setTicketId(slOrderDetail.getTicketId());
				slRefundTicketDetail.setTicketUid(slOrderDetail.getTicketUid());
				slRefundTicketDetail.setIdenttyId(slOrderDetail.getIdenttyId());
				slRefundTicketDetail.setRefundAmt(slOrderDetail.getSalePrice());
				slRefundTicketDetailList.add(slRefundTicketDetail);

				slOrderDetail.setUselessFlag("T");// 是否作废(N否 Y补票作废 T退票作废 )
				slOrderDetail.setUselessTime(new Date());
			}

			slBillDetail.setRemark("已退款");
			slBillDetail.setOutRefundNo(slRefundTicket.getRefundId());
			slBillDetail.setRefundFee(slRefundTicket.getRefundAmtSum());
			dbUtil.updateEntity("更新对账明细表", session, slBillDetail);

			slOrder.setOrderStat("1");
			dbUtil.updateEntity("更新订单状态", session, slOrder);

			dbUtil.executeSql("删除退款明细", session, "DELETE FROM SL_REFUND_TICKET_DETAIL WHERE REFUND_ID=?", slRefundTicket.getRefundId());
			dbUtil.saveOrUpdateEntity("保存退款表", session, slRefundTicket);
			dbUtil.saveEntityBatch("批量保存退款明细表", session, slRefundTicketDetailList);
			dbUtil.updateEntityBatch("更新售票信息表", session, slOrderDetailList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public void repairSaleOrder(UserBean userBean, RepairSaleOrderBean repairSaleOrderBean) throws Exception {
		long realSum = 0L; // 订单销售总金额

		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();

			String dateStr = DateUtil.formatDateToString(repairSaleOrderBean.getSaleDate(), "yyyyMMdd") + "120000";
			String pkStr = DateUtil.formatDateToString(repairSaleOrderBean.getSaleDate(), "yyyyMMdd") + DateUtil.getNowDate("HHmmssSSS");
			Date date = DateUtil.formatStringToDate(dateStr, "yyyyMMddHHmmss");

			String orderId = String.format("%dXSBL%s", repairSaleOrderBean.getClientId(), pkStr);

			SlOrder slOrder = new SlOrder();
			slOrder.setOrderId(orderId);
			slOrder.setOrderStat("0");
			slOrder.setOrderType(repairSaleOrderBean.getOrderType());
			slOrder.setPayStat("2");
			slOrder.setRemark(String.format("[%s]人工补录订单,补单时间%s", userBean.getUserId(), DateUtil.getNowDate()));
			slOrder.setSaleUserId(repairSaleOrderBean.getSaleUserId());
			slOrder.setTicketCount(repairSaleOrderBean.getTicketCount());
			slOrder.setVersionNo(new Date());
			slOrder.setSaleTime(date);

			SlOrg slOrg = null;
			if ("WL".equals(slOrder.getOrderType())) {
				slOrg = dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, repairSaleOrderBean.getOrgId());
			}

			SysTicketType sysTicketType = dbUtil.findById("", SysTicketType.class, repairSaleOrderBean.getTicketTypeId());

			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();

			String[] ticketIds = repairSaleOrderBean.getTicketIds().split("\r\n");
			int j = 0;
			for (String ticketId : ticketIds) {
				ticketId = StringUtil.trim(ticketId);
				if (StringUtil.isNull(ticketId)) {
					continue;
				}
				SlOrderDetail slOrderDetail = new SlOrderDetail();
				String orderDetailId = String.format("%dMXBL%s%d", repairSaleOrderBean.getClientId(), pkStr, j);
				slOrderDetail.setOrderDetailId(orderDetailId);
				slOrderDetail.setOrderId(orderId);
				slOrderDetail.setTicketClass("1");
				slOrderDetail.setTicketId(CommonUtil.covertLong(ticketId));
				slOrderDetail.setTicketTypeId(repairSaleOrderBean.getTicketTypeId());
				slOrderDetail.setValidateTimes(sysTicketType.getValidateTimes());
				slOrderDetail.setOriginalPrice(sysTicketType.getPrice());
				slOrderDetail.setSalePrice(sysTicketType.getPrice());
				slOrderDetail.setCheckFlag("Y");
				slOrderDetail.setUselessFlag("N");
				slOrderDetail.setVersionNo(new Date());
				slOrderDetail.setOutletId(repairSaleOrderBean.getOutletId());
				slOrderDetail.setClientId(repairSaleOrderBean.getClientId());
				slOrderDetail.setEjectUserId(repairSaleOrderBean.getSaleUserId());
				slOrderDetail.setEjectTicketStat("2");
				slOrderDetail.setEjectTicketTime(date);

				slOrderDetailList.add(slOrderDetail);

				StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
				if (ticketInfo != null) {
					ticketInfo.setStat("003");// 状态(000-未核实 001-已核实
												// 003-已销售,004-已作废)
					ticketInfo.setSaleUserId(repairSaleOrderBean.getSaleUserId());
					ticketInfo.setSaleOpeTime(date);
					strTicketInfoList.add(ticketInfo);

					slOrderDetail.setTicketUid(ticketInfo.getTicketUid());
				}

				// 保存门票场馆明细表
				List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
				for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
					SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
					slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), CommonUtil.covertLong(sysTicketTypeVenue.getId().getVenueId())));
					slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
					slOrderVenueDetailList.add(slOrderVenueDetail);
				}

				realSum = realSum + CommonUtil.covertLong(sysTicketType.getPrice());
				j++;
			}

			if (j != repairSaleOrderBean.getTicketCount()) {
				throw new ServiceException(MSG.ERROR, String.format("门票数量[%s]与票号数量[%s]不一致", repairSaleOrderBean.getTicketCount(), j));
			}

			slOrder.setDueSum(realSum);
			slOrder.setRealSum(realSum);

			SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
			slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(orderId, repairSaleOrderBean.getTicketTypeId()));
			slOrderTickettypeDetail.setTicketCount(repairSaleOrderBean.getTicketCount());
			slOrderTickettypeDetail.setEjectTicketCount(repairSaleOrderBean.getTicketCount());

			if ("WL".equals(slOrder.getOrderType())) {
				SlNetagentOrder slNetagentOrder = new SlNetagentOrder();
				slNetagentOrder.setOrderId(orderId);
				slNetagentOrder.setThirdOrderNum(repairSaleOrderBean.getThirdOrderNum());
				slNetagentOrder.setOrgId(repairSaleOrderBean.getOrgId());
				slNetagentOrder.setOpeUserId(userBean.getUserId());
				slNetagentOrder.setOpeTime(date);

				dbUtil.saveEntity("保存网络代理订单表", session, slNetagentOrder);

				SlOrgSaleinfo slOrgSaleinfo = dbUtil.findById("查询销售张数", SlOrgSaleinfo.class, new SlOrgSaleinfoId(slOrg.getOrgId(), repairSaleOrderBean.getTicketTypeId()));

				TicketTypeBeforePriceBean ticketTypeBeforePriceBean = calcTicketTypeByOrg(repairSaleOrderBean.getTicketTypeId(), slOrgSaleinfo.getSaleTotalNum(), sysTicketType.getPrice(), repairSaleOrderBean.getTicketCount());

				realSum = ticketTypeBeforePriceBean.getMinusAmt();
				slOrder.setDueSum(realSum);
				slOrder.setRealSum(realSum);

				addOrgSaleNum(session, repairSaleOrderBean.getOrgId(), repairSaleOrderBean.getTicketTypeId(), repairSaleOrderBean.getTicketCount());
				minusOrgAdvanceAmt(session, repairSaleOrderBean.getOrgId(), realSum);

				if (ticketTypeBeforePriceBean.getLv1Num() != 0) {
					for (int i = 0; i < ticketTypeBeforePriceBean.getLv1Num(); i++) {
						slOrderDetailList.get(i).setSalePrice(ticketTypeBeforePriceBean.getLv1Price());
					}
				}
				if (ticketTypeBeforePriceBean.getLv2Num() != 0) {
					for (int i = 0; i < ticketTypeBeforePriceBean.getLv2Num(); i++) {
						slOrderDetailList.get(i).setSalePrice(ticketTypeBeforePriceBean.getLv2Price());
					}
				}
			}

			SlPayType slPayType = new SlPayType();
			String payTypeId = String.format("%dPTBL%s", repairSaleOrderBean.getClientId(), pkStr);
			slPayType.setPayTypeId(payTypeId);
			slPayType.setOrderId(orderId);
			slPayType.setPayType(repairSaleOrderBean.getPayType());
			slPayType.setAmt(slOrder.getRealSum());
			slPayType.setVersionNo(new Date());

			reduceOutletInfos(session, repairSaleOrderBean.getOutletId(), repairSaleOrderBean.getTicketTypeId(), repairSaleOrderBean.getTicketCount());

			dbUtil.saveEntity("保存订单", session, slOrder);
			dbUtil.saveEntity("保存订单票种", session, slOrderTickettypeDetail);
			dbUtil.saveEntity("保存订单支付方式", session, slPayType);
			dbUtil.saveEntityBatch("保存订单明细", session, slOrderDetailList);
			dbUtil.saveEntityBatch("保存订单场馆明细", session, slOrderVenueDetailList);
			dbUtil.updateEntityBatch("更新门票销售信息", session, strTicketInfoList);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
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
	private TicketTypeBeforePriceBean calcTicketTypeByOrg(String ticketTypeId, long saleTotalNumOld, long ticketTypePrice, long minusNum) {
		TicketTypeBeforePriceBean ticketTypeBeforePriceBean = new TicketTypeBeforePriceBean();

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
			ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
			ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
			ticketTypeBeforePriceBean.setMinusAmt(ticketTypePrice * minusNum);
			return ticketTypeBeforePriceBean;
		}

		// 原销售张数和新销售张数在同一阶梯票价中
		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew != null) {
			if (sysTicketTypePriceOld.getPriceId().equals(sysTicketTypePriceNew.getPriceId())) {
				ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
				ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
				ticketTypeBeforePriceBean.setMinusAmt(CommonUtil.covertLong(sysTicketTypePriceNew.getPrice()) * minusNum);
				ticketTypeBeforePriceBean.setLv1Price(sysTicketTypePriceNew.getPrice());
				ticketTypeBeforePriceBean.setLv1Num(minusNum);
				return ticketTypeBeforePriceBean;
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

		ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
		ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
		ticketTypeBeforePriceBean.setMinusAmt(minusDllOld * priceOld + minusDllNew * priceNew);
		ticketTypeBeforePriceBean.setLv1Price(priceOld);
		ticketTypeBeforePriceBean.setLv1Num(minusDllOld);
		ticketTypeBeforePriceBean.setLv2Price(priceNew);
		ticketTypeBeforePriceBean.setLv2Num(minusDllNew);
		return ticketTypeBeforePriceBean;
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
		dbUtil.executeSql("扣减机构预付款", session, "UPDATE SL_ORG SET ADVANCE_AMT=ADVANCE_AMT-? WHERE ORG_ID=? ", saleAmt, orgId);
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
		dbUtil.executeSql("更新网点库存", session, "UPDATE STR_OUTLET_INFO SET STR_TICKET_NUM=STR_TICKET_NUM-?,last_upd_time=sysdate WHERE OUTLET_ID=? AND TICKET_TYPE_ID=?", ticketNum, outletId, ticketTypeId);
	}

	@Override
	public void validateTicketIds(UserBean userBean, long ticketCount, String ticketIds) throws ServiceException {
		String[] ticketIdArray = ticketIds.split("\n");
		StringBuilder ticketIdError = new StringBuilder();
		int j = 0;
		for (String ticketId : ticketIdArray) {
			ticketId = StringUtil.trim(ticketId);
			if (StringUtil.isNull(ticketId)) {
				continue;
			}

			StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, CommonUtil.covertLong(ticketId));
			if (ticketInfo == null) {
				if (ticketIdError.length() == 0) {
					ticketIdError.append(ticketId);
				} else {
					ticketIdError.append("," + ticketId);
				}
			}
			j++;
		}

		if (j != ticketCount) {
			throw new ServiceException(MSG.ERROR, String.format("门票数量[%s]与票号数量[%s]不一致", ticketCount, j));
		}

		if (ticketIdError.length() != 0) {
			throw new ServiceException(MSG.ERROR, String.format("票号有误[%s]", ticketIdError.toString()));
		}

	}

	@Override
	public void repairSaleOrderByIdenttyId(UserBean userBean, RepairSaleOrderBean repairSaleOrderBean) throws Exception {
		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();

			repairSaleOrderBean.setSaleUserId(userBean.getUserId());
			repairSaleOrderBean.setTicketIds(StringUtil.ToDBC(repairSaleOrderBean.getTicketIds()));// 转换半角
			repairSaleOrderBean.setTicketIds(StringUtil.trim(repairSaleOrderBean.getTicketIds()).toUpperCase());// 转换为大写

			String dateStr = DateUtil.formatDateToString(repairSaleOrderBean.getSaleDate(), "yyyyMMdd") + "120000";
			String pkStr = DateUtil.formatDateToString(repairSaleOrderBean.getSaleDate(), "yyyyMMdd") + DateUtil.getNowDate("HHmmssSSS");
			Date date = DateUtil.formatStringToDate(dateStr, "yyyyMMddHHmmss");

			String orderId = String.format("%dXSBL%s", repairSaleOrderBean.getClientId(), pkStr);

			SlOrder slOrder = new SlOrder();
			slOrder.setOrderId(orderId);
			slOrder.setOrderStat("0");
			slOrder.setOrderType(repairSaleOrderBean.getOrderType());
			slOrder.setPayStat("2");
			slOrder.setRemark(String.format("[%s]人工补录订单,补单时间%s", userBean.getUserId(), DateUtil.getNowDate()));
			slOrder.setSaleUserId(repairSaleOrderBean.getSaleUserId());
			slOrder.setTicketCount(repairSaleOrderBean.getTicketCount());
			slOrder.setVersionNo(new Date());
			slOrder.setSaleTime(date);

			SysTicketType sysTicketType = dbUtil.findById("", SysTicketType.class, repairSaleOrderBean.getTicketTypeId());

			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();

			List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, repairSaleOrderBean.getTicketTypeId());
			
			String[] ticketIds = repairSaleOrderBean.getTicketIds().split("\r\n");
			int j = 0;
			for (String ticketId : ticketIds) {
				ticketId = StringUtil.trim(ticketId);
				ticketId=ticketId.substring(0, 18);

				if (StringUtil.isNull(ticketId)) {
					continue;
				}
				SlOrderDetail slOrderDetail = new SlOrderDetail();
				String orderDetailId = String.format("%dMXBL%s%d", repairSaleOrderBean.getClientId(), pkStr, j);
				slOrderDetail.setOrderDetailId(orderDetailId);
				slOrderDetail.setOrderId(orderId);
				slOrderDetail.setTicketClass("2");
				slOrderDetail.setIdenttyId(ticketId);
				slOrderDetail.setTicketTypeId(repairSaleOrderBean.getTicketTypeId());
				slOrderDetail.setValidateTimes(sysTicketType.getValidateTimes());
				slOrderDetail.setOriginalPrice(repairSaleOrderBean.getPrice());
				slOrderDetail.setSalePrice(repairSaleOrderBean.getPrice());
				slOrderDetail.setCheckFlag("N");
				slOrderDetail.setUselessFlag("N");
				slOrderDetail.setVersionNo(new Date());
				slOrderDetail.setOutletId(repairSaleOrderBean.getOutletId());
				slOrderDetail.setClientId(repairSaleOrderBean.getClientId());
				slOrderDetail.setEjectTicketStat("1");
				slOrderDetail.setValidStartDate(repairSaleOrderBean.getValidStartDate());
				slOrderDetail.setValidEndDate(repairSaleOrderBean.getValidEndDate());

				slOrderDetailList.add(slOrderDetail);

				// 保存门票场馆明细表
				for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
					SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
					slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), CommonUtil.covertLong(sysTicketTypeVenue.getId().getVenueId())));
					slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
					slOrderVenueDetailList.add(slOrderVenueDetail);
				}

				j++;
			}

			if (j != repairSaleOrderBean.getTicketCount()) {
				throw new ServiceException(MSG.ERROR, String.format("门票数量[%s]与身份证号数量[%s]不一致", repairSaleOrderBean.getTicketCount(), j));
			}

			slOrder.setDueSum(repairSaleOrderBean.getPrice() * repairSaleOrderBean.getTicketCount());
			slOrder.setRealSum(repairSaleOrderBean.getPrice() * repairSaleOrderBean.getTicketCount());

			SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
			slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(orderId, repairSaleOrderBean.getTicketTypeId()));
			slOrderTickettypeDetail.setTicketCount(repairSaleOrderBean.getTicketCount());
			slOrderTickettypeDetail.setEjectTicketCount(0l);

			SlPayType slPayType = new SlPayType();
			String payTypeId = String.format("%dPTBL%s", repairSaleOrderBean.getClientId(), pkStr);
			slPayType.setPayTypeId(payTypeId);
			slPayType.setOrderId(orderId);
			slPayType.setPayType(repairSaleOrderBean.getPayType());
			slPayType.setAmt(slOrder.getRealSum());
			slPayType.setVersionNo(new Date());

			dbUtil.saveEntity("保存订单", session, slOrder);
			dbUtil.saveEntity("保存订单票种", session, slOrderTickettypeDetail);
			dbUtil.saveEntity("保存订单支付方式", session, slPayType);
			saveOrderDetailJdbcBatch(session, slOrderDetailList);
			saveOrderDetailVenueJdbcBatch(session, slOrderVenueDetailList);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	/**
	 * 批量保存订单明细表
	 * @param session
	 * @param slOrderDetailList
	 */
	private void saveOrderDetailJdbcBatch(Session session, final List<SlOrderDetail> slOrderDetailList) {
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement ps = null;
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" INSERT INTO SL_ORDER_DETAIL ");
					sb.append(" (ORDER_DETAIL_ID, ORDER_ID, TICKET_CLASS, TICKET_UID, IDENTTY_ID, TICKET_TYPE_ID, VALIDATE_TIMES, ORIGINAL_PRICE, SALE_PRICE, CHECK_FLAG, USELESS_FLAG, VERSION_NO, OUTLET_ID, CLIENT_ID, EJECT_USER_ID, EJECT_TICKET_STAT, EJECT_TICKET_TIME, USELESS_TIME, VALID_START_DATE, VALID_END_DATE) ");
					sb.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sb.toString());
					for (int i = 0; i < slOrderDetailList.size(); i++) {
						SlOrderDetail SlOrderDetail = slOrderDetailList.get(i);
						ps.setString(1, SlOrderDetail.getOrderDetailId());
						ps.setString(2, SlOrderDetail.getOrderId());
						ps.setString(3, SlOrderDetail.getTicketClass());
						ps.setString(4, SlOrderDetail.getTicketUid());
						ps.setString(5, SlOrderDetail.getIdenttyId());
						ps.setString(6, SlOrderDetail.getTicketTypeId());
						ps.setLong(7, SlOrderDetail.getValidateTimes());
						ps.setLong(8, SlOrderDetail.getOriginalPrice());
						ps.setLong(9, SlOrderDetail.getSalePrice());
						ps.setString(10, SlOrderDetail.getCheckFlag());
						ps.setString(11, SlOrderDetail.getUselessFlag());
						ps.setTimestamp(12, DateUtil.covertSqlTimeStamp(SlOrderDetail.getVersionNo()));
						ps.setLong(13, SlOrderDetail.getOutletId());
						ps.setLong(14, SlOrderDetail.getClientId());
						ps.setString(15, SlOrderDetail.getEjectUserId());
						ps.setString(16, SlOrderDetail.getEjectTicketStat());
						ps.setTimestamp(17, DateUtil.covertSqlTimeStamp(SlOrderDetail.getEjectTicketTime()));
						ps.setTimestamp(18, DateUtil.covertSqlTimeStamp(SlOrderDetail.getUselessTime()));
						ps.setDate(19, DateUtil.covertSqlDate(SlOrderDetail.getValidStartDate()));
						ps.setDate(20, DateUtil.covertSqlDate(SlOrderDetail.getValidEndDate()));
						ps.addBatch();
						if ((i + 1) % 2000 == 0) {
							ps.executeBatch();
							ps.clearBatch();
						}
					}
					if (slOrderDetailList.size() % 2000 != 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				} finally {
					if (ps != null) {
						try {
							ps.close();
							ps = null;
						} catch (Exception ex) {
							ps = null;
							logger.error("错误", ex);
						}
					}
				}

			}
		});
	}

	/**
	 * 批量保存订单明细场馆表
	 * @param session
	 * @param slOrderVenueDetailList
	 */
	private void saveOrderDetailVenueJdbcBatch(Session session, final List<SlOrderVenueDetail> slOrderVenueDetailList) {
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement ps = null;
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" INSERT INTO SL_ORDER_VENUE_DETAIL ");
					sb.append(" (ORDER_DETAIL_ID, VENUE_ID, REMAIN_TIMES) ");
					sb.append(" VALUES(?,?,?) ");

					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sb.toString());
					for (int i = 0; i < slOrderVenueDetailList.size(); i++) {
						SlOrderVenueDetail slOrderVenueDetail = slOrderVenueDetailList.get(i);
						ps.setString(1, slOrderVenueDetail.getId().getOrderDetailId());
						ps.setLong(2, slOrderVenueDetail.getId().getVenueId());
						ps.setLong(3, slOrderVenueDetail.getRemainTimes());

						ps.addBatch();
						if ((i + 1) % 2000 == 0) {
							ps.executeBatch();
							ps.clearBatch();
						}
					}
					if (slOrderVenueDetailList.size() % 2000 != 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				} finally {
					if (ps != null) {
						try {
							ps.close();
							ps = null;
						} catch (Exception ex) {
							ps = null;
							logger.error("错误", ex);
						}
					}
				}
			}
		});
	}
}
