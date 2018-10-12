package com.tbims.bean;

import java.util.Date;

public class RptCheckBean {

	/**
	 * 检票表id
	 */
	private String checkId;

	/**
	 * 门票类型(1-FRID、2-身份证、3-二维码)
	 */
	private String ticketClass;

	/**
	 * 票号
	 */
	private Long ticketId;

	/**
	 * 票据唯一号
	 */
	private String ticketUid;

	/**
	 * 操作时间
	 */
	private String opeTime;

	/**
	 * 终端编号
	 */
	private Long clientId;
	
	/**
	 * 终端名称
	 */
	private String clientName;

	/**
	 * 是否通过(Y是N否)
	 */
	private String passFlag;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 未通过原因
	 */
	private String nopassReason;

	/**
	 * 剩余次数
	 */
	private Long remainTimes;

	/**
	 * 版本号
	 */
	private Date versionNo;
	/**
	 * 区域编号.
	 */
	private Long regionId;
	
	

	/**
	 * 场馆编号.
	 */
	private Long venueId;

	/**
	 * 场馆编号.
	 */
	private String venueName; 
	
	/**
	 * 区域名称.
	 */
	private String regionName;


	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getVenueId() {
		return venueId;
	}

	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getTicketClass() {
		return ticketClass;
	}

	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketUid() {
		return ticketUid;
	}

	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}

	public String getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(String opeTime) {
		this.opeTime = opeTime;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getNopassReason() {
		return nopassReason;
	}

	public void setNopassReason(String nopassReason) {
		this.nopassReason = nopassReason;
	}

	public Long getRemainTimes() {
		return remainTimes;
	}

	public void setRemainTimes(Long remainTimes) {
		this.remainTimes = remainTimes;
	}

	public Date getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	
	
}
