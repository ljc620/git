package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tbims.entity.SlOrder;
import com.tbims.entity.SlOrderTickettypeDetail;
import com.tbims.entity.SlOrderTickettypeDetailId;
import com.tbims.entity.SlRefundTicket;
import com.tbims.pay.PayUtil;
import com.tbims.pay.bean.RefundOrderRequest;
import com.tbims.pay.bfbank.config.SwiftpassConfig;
import com.tbims.service.ISlOrderQueryService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;

@Service
public class SlOrderQueryService extends BaseService implements ISlOrderQueryService {

	@Autowired
	@Qualifier("payUtilByBfBank")
	PayUtil payUtil;

	@Override
	public PageBean<Map<String, Object>> querySlOrderByNOEjectList(UserBean loginUserBean, Date saleTimeBegin, Date saleTimeEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SO.ORDER_ID,                                     ");
		sql.append("       SO.ORDER_TYPE,                                   ");
		sql.append("       SO.TICKET_COUNT,                                 ");
		sql.append("       SO.REAL_SUM,                                     ");
		sql.append("       SO.REMARK,                                       ");
		sql.append("       SO.PAY_STAT,                                     ");
		sql.append("       TO_CHAR(SO.SALE_TIME,'YYYY-MM-DD HH24:MI:SS') SALE_TIME,          ");
		sql.append("       SP.PAY_TYPE,                                     ");
		sql.append("       stt.ticket_type_id,                          ");
		sql.append("       stt.ticket_type_name,                          ");
		sql.append("       SOTD.EJECT_TICKET_COUNT,                          ");
		sql.append("       SP.BANK_ID                          ");
		sql.append("  FROM SL_ORDER SO, SL_PAY_TYPE SP,SL_ORDER_TICKETTYPE_DETAIL SOTD,sys_ticket_type stt     ");
		sql.append(" WHERE SO.ORDER_ID = SP.ORDER_ID AND SO.ORDER_ID=SOTD.ORDER_ID and sotd.ticket_type_id=stt.ticket_type_id ");
		sql.append("   AND  SOTD.TICKET_COUNT<>SOTD.EJECT_TICKET_COUNT AND SO.ORDER_TYPE='ZG' AND SO.ORDER_STAT='0' AND SO.SALE_TIME <= SYSDATE-10/(24*60)          ");
		sql.append(" AND TRUNC(SO.SALE_TIME,'DD') BETWEEN TRUNC(:SALE_TIME_BEGIN,'DD') AND TRUNC(:SALE_TIME_END,'DD')");
		if (saleTimeBegin != null && saleTimeEnd != null) {
			params.put("SALE_TIME_BEGIN", saleTimeBegin);
			params.put("SALE_TIME_END", saleTimeEnd);
		} else {
			params.put("SALE_TIME_BEGIN", new Date());
			params.put("SALE_TIME_END", new Date());
		}

		sql.append("  ORDER BY SO.SALE_TIME DESC ");

		PageBean<Map<String, Object>> retList = dbUtil.queryPageToMap("查询未取票的销售订单信息 ", sql.toString(), loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return retList;
	}

	@Override
	public void refundTicketByNOEject(UserBean loginUserBean, String orderId, int refundFee) throws ServiceException {
		Session session = null;
		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SlOrder slOrder = dbUtil.findById("", SlOrder.class, orderId);

			RefundOrderRequest tradeOrderRequest = new RefundOrderRequest();
			tradeOrderRequest.setOp_user_id(SwiftpassConfig.mch_id);
			tradeOrderRequest.setMch_id(SwiftpassConfig.mch_id);
			tradeOrderRequest.setOut_refund_no(slOrder.getOrderId() + "-02");// 01:对账失败全额退款 02:手动退款(未取票的售票记录)
			tradeOrderRequest.setOut_trade_no(slOrder.getOrderId());
			tradeOrderRequest.setTotal_fee(CommonUtil.covertInt(slOrder.getRealSum()));
			tradeOrderRequest.setRefund_fee(refundFee);

			payUtil.refundOrderPay(tradeOrderRequest);

			// 保存退款信息
			SlRefundTicket slRefundTicket = new SlRefundTicket();
			slRefundTicket.setRefundId(tradeOrderRequest.getOut_refund_no());
			slRefundTicket.setOrderType(slOrder.getOrderType());
			slRefundTicket.setOrgId(tradeOrderRequest.getOp_user_id());
			slRefundTicket.setOrderId(slOrder.getOrderId());
			slRefundTicket.setTicketCount(slOrder.getTicketCount());
			slRefundTicket.setRefundAmtSum(CommonUtil.covertLong(refundFee));
			slRefundTicket.setRefundTime(new Date());

			slOrder.setOrderStat("2");
			dbUtil.updateEntity("更新订单状态", session, slOrder);

			dbUtil.saveOrUpdateEntity("保存退款表", session, slRefundTicket);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public Map<String, Object> queryRefundByNOEject(UserBean loginUserBean, String orderId, String ticketTypeId) throws ServiceException {
		SlOrder slOrder = dbUtil.findById("", SlOrder.class, orderId);
		if (!"2".equals(slOrder.getPayStat())) {
			throw new ServiceException(MSG.BF_ERROR, "该订单未支付,不允许退票");
		}

		SlOrderTickettypeDetail slOrderTickettypeDetail = dbUtil.findById("", SlOrderTickettypeDetail.class, new SlOrderTickettypeDetailId(orderId, ticketTypeId));

		Map<String, Object> saleInfoMap = dbUtil.queryFirstForMap("查询已销售的金额", "SELECT SUM(SALE_PRICE) SALE_PRICE FROM SL_ORDER_DETAIL WHERE ORDER_ID=?", orderId);
		int salePrice = CommonUtil.covertInt(saleInfoMap.get("salePrice"));

		// 计算退款张数，退款金额
		int refundNum = CommonUtil.covertInt(slOrderTickettypeDetail.getTicketCount()) - CommonUtil.covertInt(slOrderTickettypeDetail.getEjectTicketCount());
		int refundFee = CommonUtil.covertInt(slOrder.getRealSum()) - salePrice;

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("refundNum", refundNum);
		retMap.put("refundFee", refundFee);

		return retMap;
	}

}
