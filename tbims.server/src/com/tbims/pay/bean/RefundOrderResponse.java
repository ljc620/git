package com.tbims.pay.bean;

/**
 * Title: 申请退款 响应参数 <br/>
 * Description:
 * 
 * @ClassName: RefundOrderResponse
 * @author ydc
 * @date 2017年8月8日 下午6:15:23
 * 
 */
public class RefundOrderResponse {

	/**
	 * 0表示成功非0表示失败此字段是通信标识，非交易 标识，交易是否成功需要查看 result_code
	 */
	private String status;

	/**
	 * 返回信息，如非空，为错误原因签名失败参数格式校 验错误
	 */
	private String message;

	/**
	 * 0表示成功非0表示失败
	 */
	private String result_code;

	/**
	 * 具体错误码请看文档最后错误码列表
	 */
	private String err_code;

	/**
	 * 结果信息描述
	 */
	private String err_msg;

	/**
	 * 平台交易号
	 */
	private String transaction_id;

	/**
	 * 第三方订单号
	 */
	private String out_trade_no;

	/**
	 * 商户订单号
	 */
	private String out_refund_no;

	/**
	 * 平台退款单号
	 */
	private String refund_id;

	/**
	 * 退款金额
	 */
	private int refund_fee;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the result_code
	 */
	public String getResult_code() {
		return result_code;
	}

	/**
	 * @param result_code
	 *            the result_code to set
	 */
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	/**
	 * @return the err_code
	 */
	public String getErr_code() {
		return err_code;
	}

	/**
	 * @param err_code
	 *            the err_code to set
	 */
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	/**
	 * @return the err_msg
	 */
	public String getErr_msg() {
		return err_msg;
	}

	/**
	 * @param err_msg
	 *            the err_msg to set
	 */
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	/**
	 * @return the transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transaction_id
	 *            the transaction_id to set
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
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
	 * @return the refund_id
	 */
	public String getRefund_id() {
		return refund_id;
	}

	/**
	 * @param refund_id
	 *            the refund_id to set
	 */
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
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

}
