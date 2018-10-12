package com.tbims.entity;
// Generated 2017-7-23 17:52:56 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 箱库存表临时表
 */
@Entity
@Table(name = "STR_CHEST_TMP", schema = "TBIMS")
public class StrChestTmp implements java.io.Serializable {

	/**
	 * 票号
	 */
	private String chestId;

	/**
	 * 批次号
	 */
	private String batchId;

	/**
	 * 票种编号
	 */
	private String ticketTypeId;

	/**
	 * 状态(001-已入库)
	 */
	private String stat;

	/**
	 * 盒数
	 */
	private String boxNum;

	/**
	 * 门票数
	 */
	private Long ticketNum;

	/**
	 * 票起号
	 */
	private Long beginNo;

	/**
	 * 票止号
	 */
	private Long endNo;

	/**
	 * 入库人
	 */
	private String opeUserId;

	/**
	 * 操作时间
	 */
	private Date opeTime;

	public StrChestTmp() {
	}

	public StrChestTmp(String chestId) {
		this.chestId = chestId;
	}
	public StrChestTmp(String chestId, String batchId, String ticketTypeId, String stat, String boxNum, Long ticketNum, Long beginNo, Long endNo, String opeUserId, Date opeTime) {
		this.chestId = chestId;
		this.batchId = batchId;
		this.ticketTypeId = ticketTypeId;
		this.stat = stat;
		this.boxNum = boxNum;
		this.ticketNum = ticketNum;
		this.beginNo = beginNo;
		this.endNo = endNo;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/**
	 * 票号.
	 */
	@Id
	@Column(name = "CHEST_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public String getChestId() {
		return this.chestId;
	}
	/**
	 * 票号.
	 */
	public void setChestId(String chestId2) {
		this.chestId = chestId2;
	}

	/**
	 * 批次号.
	 */
	@Column(name = "BATCH_ID", length = 50)
	public String getBatchId() {
		return this.batchId;
	}
	/**
	 * 批次号.
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}
	/**
	 * 票种编号.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/**
	 * 状态(001-已入库).
	 */
	@Column(name = "STAT", length = 3)
	public String getStat() {
		return this.stat;
	}
	/**
	 * 状态(001-已入库).
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/**
	 * 盒数.
	 */
	@Column(name = "BOX_NUM", precision = 10, scale = 0)
	public String getBoxNum() {
		return this.boxNum;
	}
	/**
	 * 盒数.
	 */
	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	/**
	 * 门票数.
	 */
	@Column(name = "TICKET_NUM", precision = 10, scale = 0)
	public Long getTicketNum() {
		return this.ticketNum;
	}
	/**
	 * 门票数.
	 */
	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	/**
	 * 票起号.
	 */
	@Column(name = "BEGIN_NO", precision = 10, scale = 0)
	public Long getBeginNo() {
		return this.beginNo;
	}
	/**
	 * 票起号.
	 */
	public void setBeginNo(Long beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * 票止号.
	 */
	@Column(name = "END_NO", precision = 10, scale = 0)
	public Long getEndNo() {
		return this.endNo;
	}
	/**
	 * 票止号.
	 */
	public void setEndNo(Long endNo) {
		this.endNo = endNo;
	}

	/**
	 * 入库人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}
	/**
	 * 入库人.
	 */
	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/**
	 * 操作时间.
	 */
	@Temporal(TemporalType.DATE)
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

}
