package com.tbims.pay.bean;

/**
 *	商户附加信息，可做扩展参数
 */
public class AttachParamsBean {
	
	/**
	 * 对账标志
	 */
	String bill_flag;

	public String getBill_flag() {
		return bill_flag;
	}

	public void setBill_flag(String bill_flag) {
		this.bill_flag = bill_flag;
	}
}
