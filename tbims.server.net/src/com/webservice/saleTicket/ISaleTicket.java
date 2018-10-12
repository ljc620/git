package com.webservice.saleTicket;

import java.util.Date;

import javax.jws.WebService;

import com.webservice.CXFException;
import com.webservice.entity.OrderDetail;
import com.webservice.entity.RefundTicket;
import com.webservice.entity.TicketStatus;
import com.webservice.entity.TicketType;

/**
 * Title: 售票相关接口 <br/>
 * Description:
 * 
 * @ClassName: ISaleTicket
 * @author ydc
 * @date 2017年7月25日 上午10:48:39
 * 
 */
@WebService
public interface ISaleTicket {
	/**
	 * Title:票种查询接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	TicketType[] getTicketType(String orgID, String token, String transq) throws CXFException;

	/**
	 * Title:售票查询接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param identityCode 身份证号
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	int getTicketCountByID(String orgID, String token, String identityCode, String transq) throws CXFException;

	/**
	 * Title:售票接口 <br/>
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
	 * @return 1 成功，-1 失败 2-该订单已完成（当上传售票时出现超时的情况，客户端不知道服务端的订单状态，可以再发送该订单，如果该订单已完成则返回2- 该订单已完成）
	 */
	int saleDateSync(String orgID, String token, String orderID, int ticketCount, long realSum, Date saleTime, OrderDetail[] saleOrderDetail, String payType, String payID, String transq)
			throws CXFException;

	/**
	 * Title:门票状态查询接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param orderID 订单号
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 * @throws CXFException
	 */
	TicketStatus[] GetTicketStatusByOrderID(String orgID, String token, String orderID, String transq) throws CXFException;

	/**
	 * Title: 退票接口<br/>
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
	int tradeRefund(String orgID, String token, String orderID, String refundID, int ticketCount, long refundAMTSum, Date refundTime, RefundTicket[] refundTickets, String transq) throws CXFException;

}
