package com.tbims.pay;

import com.tbims.pay.bean.BillOrderRequest;
import com.tbims.pay.bean.CancelOrderRequest;
import com.tbims.pay.bean.OrderPayRequest;
import com.tbims.pay.bean.QueryOrderRequest;
import com.tbims.pay.bean.QueryRefundOrderRequest;
import com.tbims.pay.bean.RefundOrderRequest;
import com.zhming.support.exception.ServiceException;

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
	 * @throws ServiceException 
	 */
	public void orderPay(OrderPayRequest orderPayRequest) throws ServiceException;

	/** 
	 * Title:订单查询接口<br/>
	* Description: 
	* @param orderPayQueryRequest
	* @return  1-待支付 2-已支付 3-支付失败
	* @throws ServiceException
	*/ 
	public int queryOrderPay(QueryOrderRequest orderPayQueryRequest) throws ServiceException;

	/**
	 * Title:撤销订单 <br/>
	 * Description:
	 * 
	 * @param cancelOrderRequest
	 * @throws ServiceException 
	 */
	public void cancelOrderPay(CancelOrderRequest cancelOrderRequest) throws ServiceException;

	/**
	 * Title:申请退款 <br/>
	 * Description:
	 * 
	 * @param tradeOrderRequest
	 * @throws ServiceException 
	 */
	public void refundOrderPay(RefundOrderRequest tradeOrderRequest) throws ServiceException;

	/**
	 * Title:退款订单查询 <br/>
	 * Description:
	 * 
	 * @param queryTradeOrderReques
	 * @throws ServiceException 
	 */
	public void queryRefundOrderPay(QueryRefundOrderRequest queryTradeOrderRequest) throws ServiceException;
	
	/**
	 * 对账
	 * @param tranDt
	 * @throws ServiceException 
	 */
	public void billOrder(BillOrderRequest billOrderBean) throws ServiceException;

}
