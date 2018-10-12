package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 票种表
 */
@Entity
@Table(name = "SYS_TICKET_TYPE", schema = "TBIMS")
public class SysTicketType implements java.io.Serializable {

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	/** 
	 * 票种名称.
	 */
	private String ticketTypeName;

	/** 
	 * 是否团体.
	 */
	private String teamFlag;

	/** 
	 * 可用次数.
	 */
	private Long validateTimes;

	/** 
	 * 是否优惠票.
	 */
	private String lessFlag;

	/** 
	 * 日夜场.
	 */
	private String dayNightFlag;

	/** 
	 * 是否销售日当日有效(Y是N否).
	 */
	private String dayValidateFlag;

	/** 
	 * 票价.
	 */
	private Long price;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	/** 
	 * 版本号.
	 */
	private Date versionNo;
	
	/** 
	 * 票种状态(Y正常N停用)
	 */
	private String ticketTypeStat;

	public SysTicketType() {
	}

	public SysTicketType(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public SysTicketType(String ticketTypeId, String ticketTypeName, String teamFlag, Long validateTimes, String lessFlag, String dayNightFlag, String dayValidateFlag, Long price, String opeUserId, Date opeTime, Date versionNo) {
		this.ticketTypeId = ticketTypeId;
		this.ticketTypeName = ticketTypeName;
		this.teamFlag = teamFlag;
		this.validateTimes = validateTimes;
		this.lessFlag = lessFlag;
		this.dayNightFlag = dayNightFlag;
		this.dayValidateFlag = dayValidateFlag;
		this.price = price;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.versionNo = versionNo;
	}

	/** 
	 * 票种编号.
	 */
	@Id
	@Column(name = "TICKET_TYPE_ID", unique = true, nullable = false, length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 票种名称.
	 */
	@Column(name = "TICKET_TYPE_NAME", length = 200)
	public String getTicketTypeName() {
		return this.ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	/** 
	 * 是否团体.
	 */
	@Column(name = "TEAM_FLAG", length = 1)
	public String getTeamFlag() {
		return this.teamFlag;
	}

	public void setTeamFlag(String teamFlag) {
		this.teamFlag = teamFlag;
	}

	/** 
	 * 可用次数.
	 */
	@Column(name = "VALIDATE_TIMES", precision = 3, scale = 0)
	public Long getValidateTimes() {
		return this.validateTimes;
	}

	public void setValidateTimes(Long validateTimes) {
		this.validateTimes = validateTimes;
	}

	/** 
	 * 是否优惠票.
	 */
	@Column(name = "LESS_FLAG", length = 1)
	public String getLessFlag() {
		return this.lessFlag;
	}

	public void setLessFlag(String lessFlag) {
		this.lessFlag = lessFlag;
	}

	/** 
	 * 日夜场.
	 */
	@Column(name = "DAY_NIGHT_FLAG", length = 1)
	public String getDayNightFlag() {
		return this.dayNightFlag;
	}

	public void setDayNightFlag(String dayNightFlag) {
		this.dayNightFlag = dayNightFlag;
	}

	/** 
	 * 是否销售日当日有效(Y是N否).
	 */
	@Column(name = "DAY_VALIDATE_FLAG", length = 1)
	public String getDayValidateFlag() {
		return this.dayValidateFlag;
	}

	public void setDayValidateFlag(String dayValidateFlag) {
		this.dayValidateFlag = dayValidateFlag;
	}

	/** 
	 * 票价.
	 */
	@Column(name = "PRICE", precision = 4, scale = 0)
	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
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
	 * 票种状态(Y正常N停用).
	 */
	@Column(name = "TICKET_TYPE_STAT", length = 1)
	public String getTicketTypeStat() {
		return this.ticketTypeStat;
	}
	/** 
	 * 票种状态(Y正常N停用).
	 */
	public void setTicketTypeStat(String ticketTypeStat) {
		this.ticketTypeStat = ticketTypeStat;
	}

}
