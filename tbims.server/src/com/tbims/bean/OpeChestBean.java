package com.tbims.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class OpeChestBean {
	/** 
	 * 箱号
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
	 * 票种名称
	 */
	private String ticketTypeName;

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
	 * 入库人名称
	 */
	private String userName;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public String getChestId() {
		return chestId;
	}

	public void setChestId(String chestId) {
		this.chestId = chestId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Long getBeginNo() {
		return beginNo;
	}

	public void setBeginNo(Long beginNo) {
		this.beginNo = beginNo;
	}

	public Long getEndNo() {
		return endNo;
	}

	public void setEndNo(Long endNo) {
		this.endNo = endNo;
	}

	public String getOpeUserId() {
		return opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OPE_TIME", length = 7)
	@JsonSerialize(using = DateSerializer.class)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}
	
}
