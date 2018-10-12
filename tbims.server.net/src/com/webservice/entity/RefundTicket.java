package com.webservice.entity;

/**
 * Title:退票接口 <br/>
 * Description:
 * 
 * @ClassName: RefundTicket
 * @author ydc
 * @date 2017年7月25日 上午11:50:29
 * 
 */
public class RefundTicket {
	/**
	 * 身份证号
	 */
	private String identityCode;
	/**
	 * 退款金额
	 */
	private long refundAmt;

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public long getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(long refundAmt) {
		this.refundAmt = refundAmt;
	}
}
