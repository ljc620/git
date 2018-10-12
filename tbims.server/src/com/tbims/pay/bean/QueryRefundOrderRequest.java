package com.tbims.pay.bean;

/**
* Title: 查询退款请求参数  <br/>
* Description: 
* @ClassName: QueryTradeOrderRequest
* @author ydc
* @date 2017年8月8日 下午6:49:28
* 
*/
public class QueryRefundOrderRequest {

	/**
	 * 商户号，由平台分配
	 */
	private String mch_id;

	/**
	 * 商户系统内部的订单号, out_trade_no和transaction_id至少一个 必填，同时存在时transaction_id优先
	 */
	private String out_trade_no;

	/**
	 * 平台单号, out_trade_no和transaction_id至少一个必填，同时存 在时transaction_id优先
	 */
	private String transaction_id;

	/**
	 * 商户退款单号
	 */
	private String out_refund_no;

	/**
	 * 平台退款单号关于refund_id、out_refund_no、 out_trade_no 、transaction_id <br>
	 * 四个参数必填一个， 如果同时 存在优先级为： <br>
	 * refund_id>out_refund_no>transaction_id>out_trade_no <br>
	 * 特殊说明：如果是支付宝，refund_id、out_refund_no 必填其中一个
	 */
	private String refund_id;

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

}
