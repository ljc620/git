package com.tbims.pay.bean;

/**
 * Title: 申请退款 <br/>
 * Description:
 * 
 * @ClassName: TradeOrderRequest
 * @author ydc
 * @date 2017年8月8日 下午6:49:37
 * 
 */
public class RefundOrderRequest {
	/**
	 * 商户号，由平台分配
	 */
	private String mch_id;

	/**
	 * 商户系统内部的订单号, out_trade_no和 transaction_id至少一个必填，同时存在时 transaction_id优先
	 */
	private String out_trade_no;

	/**
	 * 商户退款单号，32个字符内、可包含字母,确保在商户系统唯一。同个退款单号多次请求，平台当一个单处理，只会退一次款。如果出现退款不成功，
	 * 请采用原退款单号重新发起，避免出现重复退款。
	 */
	private String out_refund_no;

	/**
	 * 订单总金额，单位为分
	 */
	private int total_fee;

	/**
	 * 退款总金额,单位为分,可以做部分退款
	 */
	private int refund_fee;

	/**
	 * 操作员帐号,默认为商户号
	 */
	private String op_user_id;
	
	/**
	 * @return the mch_id
	 */
	public String getMch_id() {
		return mch_id;
	}

	/**
	 * @param mch_id
	 *            the mch_id to set
	 */
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no
	 *            the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @return the out_refund_no
	 */
	public String getOut_refund_no() {
		return out_refund_no;
	}

	/**
	 * @param out_refund_no
	 *            the out_refund_no to set
	 */
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	/**
	 * @return the total_fee
	 */
	public int getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee
	 *            the total_fee to set
	 */
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @return the refund_fee
	 */
	public int getRefund_fee() {
		return refund_fee;
	}

	/**
	 * @param refund_fee
	 *            the refund_fee to set
	 */
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	/**
	 * @return the op_user_id
	 */
	public String getOp_user_id() {
		return op_user_id;
	}

	/**
	 * @param op_user_id the op_user_id to set
	 */
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

}
