package com.tbims.pay.bean;

/**
 * Title: 撤销订单请求参数 <br/>
 * Description:
 * 
 * @ClassName: CancelOrderRequest
 * @author ydc
 * @date 2017年8月8日 下午6:50:05
 * 
 */
public class CancelOrderRequest {
	/**
	 * 商户号，由平台分配
	 */
	private String mch_id;
	/**
	 * 商户系统内部的订单号 ,32个字符内、 可包含字母,确保在商户系统唯一
	 */
	private String out_trade_no;

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

}
