package com.tbims.db.entity;
// Generated 2017-6-28 15:38:42 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 黑名单表
 */
@Entity
@Table(name = "SYS_BLACK_LIST", schema = "TBIMS")
public class SysBlackList implements java.io.Serializable {

	/** 
	 * 黑名单ID
	 */
	private String blackListId;

	/** 
	 * 类型(1-员工卡或2-票)
	 */
	private String cardType;

	/** 
	 * 挂失日期
	 */
	private Date lossDt;

	/** 
	 * 票号或员工卡编号
	 */
	private String ticketId;

	/** 
	 * 芯片ID
	 */
	private String chipId;

	/** 
	 * 状态(Y启用N取消)
	 */
	private String stat;

	/** 
	 * 挂失原因
	 */
	private String lossReason;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	public SysBlackList() {
	}

	public SysBlackList(String blackListId) {
		this.blackListId = blackListId;
	}
	public SysBlackList(String blackListId, String cardType, Date lossDt, String ticketId, String chipId, String stat, String lossReason, String opeUserId, Date opeTime, Date versionNo) {
		this.blackListId = blackListId;
		this.cardType = cardType;
		this.lossDt = lossDt;
		this.ticketId = ticketId;
		this.chipId = chipId;
		this.stat = stat;
		this.lossReason = lossReason;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.versionNo = versionNo;
	}

	/** 
	 * 黑名单ID.
	 */
	@Id
	@Column(name = "BLACK_LIST_ID", unique = true, nullable = false, length = 50)
	public String getBlackListId() {
		return this.blackListId;
	}
	/** 
	 * 黑名单ID.
	 */
	public void setBlackListId(String blackListId) {
		this.blackListId = blackListId;
	}

	/** 
	 * 类型(1-员工卡或2-票).
	 */
	@Column(name = "CARD_TYPE", length = 1)
	public String getCardType() {
		return this.cardType;
	}
	/** 
	 * 类型(1-员工卡或2-票).
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/** 
	 * 挂失日期.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LOSS_DT", length = 7)
	public Date getLossDt() {
		return this.lossDt;
	}
	/** 
	 * 挂失日期.
	 */
	public void setLossDt(Date lossDt) {
		this.lossDt = lossDt;
	}

	/** 
	 * 票号或员工卡编号.
	 */
	@Column(name = "TICKET_ID", length = 20)
	public String getTicketId() {
		return this.ticketId;
	}
	/** 
	 * 票号或员工卡编号.
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/** 
	 * 芯片ID.
	 */
	@Column(name = "CHIP_ID", length = 20)
	public String getChipId() {
		return this.chipId;
	}
	/** 
	 * 芯片ID.
	 */
	public void setChipId(String chipId) {
		this.chipId = chipId;
	}

	/** 
	 * 状态(Y启用N取消).
	 */
	@Column(name = "STAT", length = 1)
	public String getStat() {
		return this.stat;
	}
	/** 
	 * 状态(Y启用N取消).
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/** 
	 * 挂失原因.
	 */
	@Column(name = "LOSS_REASON", length = 200)
	public String getLossReason() {
		return this.lossReason;
	}
	/** 
	 * 挂失原因.
	 */
	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}
	/** 
	 * 操作人.
	 */
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
	/** 
	 * 操作时间.
	 */
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
	/** 
	 * 版本号.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

}
