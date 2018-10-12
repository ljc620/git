package com.tbims.pay;

import org.hibernate.Session;

import com.tbims.pay.bean.BillOrderRequest;
import com.tbims.pay.bean.CancelOrderRequest;
import com.tbims.pay.bean.OrderPayRequest;
import com.tbims.pay.bean.QueryOrderRequest;
import com.tbims.pay.bean.QueryRefundOrderRequest;
import com.tbims.pay.bean.RefundOrderRequest;
import com.tbims.rpc.entity.RPCException;

/**
 * Title: 第三方支付工具类 <br/>
 * Description:
 * 
 * @ClassName: PayUtil
 * @author ydc
 * @date 2017年8月8日 下午5:59:10
 * 
 */
public interface PayUtil {

	/**
	 * Title:订单支付接口 <br/>
	 * Description:
	 * 
	 * @param orderPayRequest
	 * @throws RPCException
	 */
	public int orderPay(Session session, OrderPayRequest orderPayRequest) throws RPCException;

	/**
	 * Title:订单查询接口<br/>
	 * Description:
	 * 
	 * @param orderPayQueryRequest
	 * @return 1-待支付 2-已支付 3-支付失败
	 * @throws RPCException
	 */
	public int queryOrderPay(Session session, QueryOrderRequest orderPayQueryRequest) throws RPCException;

	/**
	 * Title:撤销订单 <br/>
	 * Description:
	 * 
	 * @param cancelOrderRequest
	 * @throws RPCException
	 */
	public void cancelOrderPay(CancelOrderRequest cancelOrderRequest) throws RPCException;

	/**
	 * Title:申请退款 <br/>
	 * Description:
	 * 
	 * @param tradeOrderRequest
	 * @throws RPCException
	 */
	public void refundOrderPay(RefundOrderRequest tradeOrderRequest) throws RPCException;

	/**
	 * Title:退款订单查询 <br/>
	 * Description:
	 * 
	 * @param queryTradeOrderReques
	 * @throws RPCException
	 */
	public void queryRefundOrderPay(QueryRefundOrderRequest queryTradeOrderRequest) throws RPCException;

	/**
	 * 对账
	 * 
	 * @param tranDt
	 * @throws RPCException
	 */
	public void billOrder(BillOrderRequest billOrderBean) throws RPCException;

}
