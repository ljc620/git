package com.tbims.pay.bean;

/**
 * Title: 订单支付响应参数 <br/>
 * Description:
 * 
 * @ClassName: OrderPayEntity
 * @author ydc
 * @date 2017年8月8日 下午5:24:30
 * 
 */
public class OrderPayResponse {

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
	 * MD5签名结果，详见“安全规范”
	 */
	private String sign;

	/**
	 * 支付结果：0—成功；其它—失败
	 */
	private String need_query;

	/**
	 * 平台交易号
	 */
	private String transaction_id;

	/**
	 * 第三方订单号
	 */
	private String out_transaction_id;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNeed_query() {
		return need_query;
	}

	public void setNeed_query(String need_query) {
		this.need_query = need_query;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_transaction_id() {
		return out_transaction_id;
	}

	public void setOut_transaction_id(String out_transaction_id) {
		this.out_transaction_id = out_transaction_id;
	}

}
