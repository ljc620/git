package com.webservice.saleTicket;

import java.util.Date;

import com.webservice.entity.OrderDetail;
import com.webservice.entity.RefundTicket;

public interface ISaleTicketSign {
	/**
	 * Title:票种查询签名接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	String getTicketTypeSign(String orgID, String token, String transq);

	/**
	 * Title:售票查询签名接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param identityCode 身份证号
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	String getTicketCountByIDSign(String orgID, String token, String identityCode, String transq);

	/**
	 * Title:售票签名接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param orderID 订单号
	 * @param ticketCount 销售门票张数
	 * @param realSum 销售金额（整个订单金额合计）
	 * @param saleTime 销售日期
	 * @param saleOrderDetail 销售明细
	 * @param payType 支付类型（01-现金，02-POS，03-微信，04-支付宝）
	 * @param payID 第三方支付单号
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	String saleDateSyncSign(String orgID, String token, String orderID, int ticketCount, long realSum, Date saleTime, OrderDetail[] saleOrderDetail, String payType, String payID, String transq);

	/**
	 * Title:门票状态查询签名接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param orderID 订单号
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	String GetTicketStatusByOrderIDSign(String orgID, String token, String orderID, String transq);

	/**
	 * Title: 退票签名接口<br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param orderID 原购票订单号
	 * @param refundID 退款单号(确保唯一)
	 * @param ticketCount 退款门票张数
	 * @param refundAMTSum 退款金额（整个退款金额合计）
	 * @param refundTime 退款日期时间
	 * @param refundTickets 退款门票明细
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	String tradeRefundSign(String orgID, String token, String orderID, String refundID, int ticketCount, long refundAMTSum, Date refundTime, RefundTicket[] refundTickets, String transq);

}
