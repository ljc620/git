package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 票种检票规则表
 */
@Entity
@Table(name = "SYS_TICKET_TYPE_RULE", schema = "TBIMS")
public class SysTicketTypeRule implements java.io.Serializable {

	/** 
	 * 检票规则编号.
	 */
	private String ruleId;

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	/** 
	 * 有效开始日期.
	 */
	private Date beginDt;

	/** 
	 * 有效结束日期.
	 */
	private Date endDt;

	/** 
	 * 有效开始时间(秒).
	 */
	private Long beginTime;

	/** 
	 * 有效结束时间(秒).
	 */
	private Long endTime;

	/** 
	 * 版本号.
	 */
	private Date versionNo;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;
	
	/**
	*有效星期(0-6位分别对应星期日至星期六(对应位1代表有效 0代表无效)
	*/
	private String validWeek;

	public SysTicketTypeRule() {
	}

	public SysTicketTypeRule(String ruleId) {
		this.ruleId = ruleId;
	}
	public SysTicketTypeRule(String ruleId, String ticketTypeId, Date beginDt, Date endDt, Long beginTime, Long endTime, Date versionNo, String opeUserId, Date opeTime,String validWeek) {
		this.ruleId = ruleId;
		this.ticketTypeId = ticketTypeId;
		this.beginDt = beginDt;
		this.endDt = endDt;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.versionNo = versionNo;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.validWeek=validWeek;
	}

	/** 
	 * 检票规则编号.
	 */
	@Id
	@Column(name = "RULE_ID", unique = true, nullable = false, length = 60)
	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 有效开始日期.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEGIN_DT", length = 7)
	public Date getBeginDt() {
		return this.beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	/** 
	 * 有效结束日期.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DT", length = 7)
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	/** 
	 * 有效开始时间(秒).
	 */
	@Column(name = "BEGIN_TIME", precision = 5, scale = 0)
	public Long getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	/** 
	 * 有效结束时间(秒).
	 */
	@Column(name = "END_TIME", precision = 5, scale = 0)
	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/** 
	 * 操作时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	/**
	*有效星期(0-6位分别对应星期日至星期六(对应位1代表有效 0代表无效)
	*/
	@Column(name = "VALID_WEEK", length = 10)
	public String getValidWeek() {
		return validWeek;
	}

	/**
	*有效星期(0-6位分别对应星期日至星期六(对应位1代表有效 0代表无效)
	*/
	public void setValidWeek(String validWeek) {
		this.validWeek = validWeek;
	}

}
