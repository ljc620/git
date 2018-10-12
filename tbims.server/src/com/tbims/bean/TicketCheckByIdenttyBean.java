package com.tbims.bean;

/**
* Title: 身份证检票记录列表  <br/>
* Description: 
* @ClassName: TicketCheckByIdenttyBean
* @author ydc
* @date 2017年9月15日 上午10:02:35
* 
*/
public class TicketCheckByIdenttyBean {
	/** 检票表id */
	private String checkId;
	/** 身份证号 */
	private String ticketUid;
	/** 场馆编号 */
	private String venueId;
	/** 场馆名称 */
	private String venueName;
	/** 终端编号 */
	private String clientId;
	/** 终端名称 */
	private String clientName;
	/** 是否通过(Y是N否) */
	private String passFlag;
	/** 未通过原因 */
	private String nopassReason;
	/** 剩余次数 */
	private String remainTimes;
	/** 票种编号 */
	private String ticketTypeId;
	/** 票种名称 */
	private String ticketTypeName;
	/** 检票时间 */
	private String opeTime;
	/** 销售单号 */
	private String orderId;

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getTicketUid() {
		return ticketUid;
	}

	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}

	public String getVenueId() {
		return venueId;
	}

	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getNopassReason() {
		return nopassReason;
	}

	public void setNopassReason(String nopassReason) {
		this.nopassReason = nopassReason;
	}

	public String getRemainTimes() {
		return remainTimes;
	}

	public void setRemainTimes(String remainTimes) {
		this.remainTimes = remainTimes;
	}

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	public String getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(String opeTime) {
		this.opeTime = opeTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
