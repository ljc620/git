package com.tbims.pay.bean;

/**
* Title: 查询订单请求参数  <br/>
* Description: 
* @ClassName: QueryOrderRequest
* @author ydc
* @date 2017年8月8日 下午6:15:23
* 
*/
public class QueryOrderRequest {

	/**
	 * 商户号，由平台分配
	 */
	private String mch_id;

	/**
	 * 商户系统内部的订单号, out_trade_no和 transaction_id至少一个必填，同时存在时 transaction_id优先
	 */
	private String out_trade_no;

	/**
	 * 平台交易号, out_trade_no和transaction_id至少一 个必填，同时存在时transaction_id优先
	 */
	private String transaction_id;

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
}
