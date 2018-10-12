package com.tbims.pay.bean;

/**
 * 对账明细
 */
public enum BillOrderEM {

	/**
	 * 总交易单数
	 */
	TOTAL_ORDER_NUM(0),
	/**
	 * 总交易额
	 */
	TOTAL_ORDER_FEE(1),
	/**
	 * 总退款金额
	 */
	TOTAL_REFUND_FEE(2),
	/**
	 * 总手续费金额
	 */
	TOTAL_SERVICE_FEE(4),
	/**
	 * 总实收金额
	 */
	TOTAL_COLLECT_FEE(5);

	private int value;

	private BillOrderEM(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
