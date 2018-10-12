package com.tbims.pay.bean;

/**
 * Title: 订单支付请求参数 <br/>
 * Description:
 * 
 * @ClassName: OrderPayEntity
 * @author ydc
 * @date 2017年8月8日 下午5:24:30
 * 
 */
public class OrderPayRequest {

	/**
	 * 商户号，由平台分配 String(32)
	 */
	private String mch_id;

	/**
	 * 商户系统内部的订单号 ,5到32个字符、 只能包含字 母数字或者下划线，区分大小写，确保在商户系统 唯一 String(32)
	 */
	private String out_trade_no;

	/**
	 * 商品描述 String(127)
	 */
	private String body;

	/**
	 * 总金额，以分为单位，不允许包含任何字符
	 */
	private int total_fee;

	/**
	 * 扫码支付授权码， 设备读取用户展示的条码或者二维码信息
	 */
	private String auth_code;

	/**
	 * 订单生成的机器 IP
	 */
	private String mch_create_ip;

	/**
	 * 终端设备号，商户自定义。特别说明：对于QQ钱包 支付，此参数必传，否则会报错。
	 */
	private String device_info;

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getMch_create_ip() {
		return mch_create_ip;
	}

	public void setMch_create_ip(String mch_create_ip) {
		this.mch_create_ip = mch_create_ip;
	}

}
