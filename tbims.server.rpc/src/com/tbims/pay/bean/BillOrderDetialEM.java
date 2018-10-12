package com.tbims.pay.bean;

/**
 * 对账明细
 */
public enum BillOrderDetialEM {
	/** 交易时间 */
	TRAN_TIME(0),
	/** 商户号 */
	MCH_ID(4),
	/** 平台订单号 */
	TRANSACTION_ID(6),
	/** 第三方订单号 */
	OUT_TRANSACTION_ID(7),
	/** 商户订单号 */
	OUT_TRADE_NO(8),
	/** 交易类型 	如：pay.weixin.native/pay.weixin.micropay/pay.weixin.jspay */
	TRAN_TYPE(10),
	/** 交易状态 	支付成功/转入退款 */
	TRAN_STATUS(11),
	/** 总金额 */
	ORDER_FEE(14),
	/** 平台退款单号 */
	REFUND_ID(16),
	/** 商户退款单号 */
	OUT_REFUND_NO(17),
	/** 退款金额 */
	REFUND_FEE(18),
	/** 退款类型 */
	REFUND_TYPE(20),
	/** 退款状态  退款成功*/
	REFUND_STATUS(21),
	/** 手续费 */
	SERVICE_FEE(24),
	/** 实收金额 */
	COLLECT_FEE(35),
	/** 商品名称	原样返回提交支付时的body参数的值 */
	BODY(22),
	/** 商户数据包	原样返回提交支付时的attach参数的值 */
	ATTACH(23);
	
	private int value;

	private BillOrderDetialEM(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
