package com.webservice.entity;

/**
 * Title: 3.1.3 门票状态实体 <br/>
 * Description:
 * 
 * @ClassName: TicketStatus
 * @author ydc
 * @date 2017年8月2日 下午2:16:01
 * 
 */
public class TicketStatus {
	/**
	 * 身份证号
	 */
	private String identityCode;

	/**
	 * 门票状态 0-未使用,1-已出票 (已换取实体票,不能再退票),2-已检票,3-已作废（退票）
	 */
	private int status;

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
