package com.tbims.pay.bean;

/**
* Title: 申请退款  <br/>
* Description: 
* @ClassName: TradeOrderRequest
* @author ydc
* @date 2017年8月8日 下午6:49:37
* 
*/
public class TradeOrderRequest {
	/**
	 * 商户号，由平台分配
	 */
	private String mch_id;

	/**
	 * 商户系统内部的订单号, out_trade_no和 transaction_id至少一个必填，同时存在时 transaction_id优先
	 */
	private String out_trade_no;

	/**
	 * 订单总金额，单位为分
	 */
	private int total_fee;

	/**
	 * 退款总金额,单位为分,可以做部分退款
	 */
	private int refund_fee;

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

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public int getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

}
