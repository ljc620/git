package com.tbims.pay.bean;

/**
 * 对账单下载请求参数
 */
public class BillOrderRequest {

	/**
	 * 商户号，由平台分配
	 */
	private String mch_id;

	private String bill_date;

	/**
	 * ALL：全部;  SUCCESS：成功;  REFUND：退款
	 */
	private String bill_type;

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getBill_date() {
		return bill_date;
	}

	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

}
