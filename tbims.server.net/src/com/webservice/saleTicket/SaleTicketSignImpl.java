package com.webservice.saleTicket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.webservice.entity.OrderDetail;
import com.webservice.entity.RefundTicket;
import com.webservice.util.SignUtil;

/**
 * 售票接口数据签名，待签名内容的顺序必须保持一致，否则校验会报错误
 * */
public class SaleTicketSignImpl implements ISaleTicketSign{

	@Override
	public String getTicketTypeSign(String orgID, String token, String transq) {
		StringBuffer sb=new StringBuffer();
		sb.append(orgID);
		sb.append(transq);
		return SignUtil.sign(sb.toString(), token);
	}

	@Override
	public String getTicketCountByIDSign(String orgID, String token, String identityCode, String transq) {
		StringBuffer sb=new StringBuffer();
		sb.append(orgID);
		sb.append(identityCode);
		sb.append(transq);
		return SignUtil.sign(sb.toString(), token);
	}

	@Override
	public String saleDateSyncSign(String orgID, String token, String orderID, int ticketCount, long realSum, Date saleTime, OrderDetail[] saleOrderDetail, String payType, String payID,
			String transq) {
		StringBuffer sb=new StringBuffer();
		sb.append(orgID);
		sb.append(orderID);
		sb.append(String.valueOf(ticketCount));
		sb.append(String.valueOf(realSum));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		sb.append(formatter.format(saleTime));
		sb.append(payType);
		sb.append(payID);
		sb.append(transq);
		for(OrderDetail od : saleOrderDetail) {
			sb.append(od.getIdentityCode());
			sb.append(od.getTicketTypeId());
			sb.append(String.valueOf(od.getSalePrice()));
			sb.append(formatter.format(od.getStartDate()));
			sb.append(formatter.format(od.getEndDate()));
		}
		return SignUtil.sign(sb.toString(), token);
	}

	@Override
	public String GetTicketStatusByOrderIDSign(String orgID, String token, String orderID, String transq) {
		StringBuffer sb=new StringBuffer();
		sb.append(orgID);
		sb.append(orderID);
		sb.append(transq);
		return SignUtil.sign(sb.toString(), token);
	}

	@Override
	public String tradeRefundSign(String orgID, String token, String orderID, String refundID, int ticketCount, long refundAMTSum, Date refundTime, RefundTicket[] refundTickets, String transq) {
		StringBuffer sb=new StringBuffer();
		sb.append(orgID);
		sb.append(orderID);
		sb.append(refundID);
		sb.append(String.valueOf(ticketCount));
		sb.append(String.valueOf(refundAMTSum));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		sb.append(formatter.format(refundTime));
		sb.append(transq);
		for(RefundTicket rt : refundTickets) {
			sb.append(rt.getIdentityCode());
			sb.append(String.valueOf(rt.getRefundAmt()));
		}
		return SignUtil.sign(sb.toString(), token);
	}

}
