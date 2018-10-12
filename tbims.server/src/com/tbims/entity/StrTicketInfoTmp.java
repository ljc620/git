package com.tbims.entity;
// Generated 2017-7-23 18:32:01 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 门票库存表临时表
 */
@Entity
@Table(name = "STR_TICKET_INFO_TMP", schema = "TBIMS")
public class StrTicketInfoTmp implements java.io.Serializable {

	/**
	 * 票号
	 */
	private Long ticketId;

	/**
	 * 票据唯一号
	 */
	private String ticketUid;

	/**
	 * 芯片ID
	 */
	private String chipId;

	/**
	 * 箱号
	 */
	private String chestId;

	/**
	 * 盒号
	 */
	private String boxId;

	/**
	 * 批次号
	 */
	private String batchId;

	/**
	 * 票种编号
	 */
	private String ticketTypeId;

	/**
	 * 状态(000-未核实 001-已核实 003-已销售,004-已作废,005-配送中,006-已接收)
	 */
	private String stat;

	/**
	 * 入库人
	 */
	private String opeUserId;

	/**
	 * 入库时间
	 */
	private Date opeTime;

	/**
	 * 售票人
	 */
	private String saleUserId;

	/**
	 * 售票时间
	 */
	private Date saleOpeTime;

	/**
	 * 库房编号(网点编号或空为票务中心)
	 */
	private String storeId;

	/**
	 * 出库时间
	 */
	private Date deliveryTime;

	/**
	 * 签收时间
	 */
	private Date signTime;

	public StrTicketInfoTmp() {
	}

	public StrTicketInfoTmp(Long ticketId) {
		this.ticketId = ticketId;
	}
	public StrTicketInfoTmp(Long ticketId, String ticketUid, String chipId, String chestId, String boxId, String batchId, String ticketTypeId, String stat, String opeUserId, Date opeTime, String saleUserId, Date saleOpeTime, String storeId, Date deliveryTime, Date signTime) {
		this.ticketId = ticketId;
		this.ticketUid = ticketUid;
		this.chipId = chipId;
		this.chestId = chestId;
		this.boxId = boxId;
		this.batchId = batchId;
		this.ticketTypeId = ticketTypeId;
		this.stat = stat;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.saleUserId = saleUserId;
		this.saleOpeTime = saleOpeTime;
		this.storeId = storeId;
		this.deliveryTime = deliveryTime;
		this.signTime = signTime;
	}

	/**
	 * 票号.
	 */
	@Id
	@Column(name = "TICKET_ID", unique = true, nullable = false, precision = 15, scale = 0)
	public Long getTicketId() {
		return this.ticketId;
	}
	/**
	 * 票号.
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * 票据唯一号.
	 */
	@Column(name = "TICKET_UID", length = 50)
	public String getTicketUid() {
		return this.ticketUid;
	}
	/**
	 * 票据唯一号.
	 */
	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
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
	 * 箱号.
	 */
	@Column(name = "CHEST_ID", precision = 10, scale = 0)
	public String getChestId() {
		return this.chestId;
	}
	/**
	 * 箱号.
	 */
	public void setChestId(String chestId) {
		this.chestId = chestId;
	}

	/**
	 * 盒号.
	 */
	@Column(name = "BOX_ID", precision = 10, scale = 0)
	public String getBoxId() {
		return this.boxId;
	}
	/**
	 * 盒号.
	 */
	public void setBoxId(String boxId) {
		this.boxId = boxId;
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
	 * 状态(000-未核实 001-已核实 003-已销售,004-已作废,005-配送中,006-已接收).
	 */
	@Column(name = "STAT", length = 3)
	public String getStat() {
		return this.stat;
	}
	/**
	 * 状态(000-未核实 001-已核实 003-已销售,004-已作废,005-配送中,006-已接收).
	 */
	public void setStat(String stat) {
		this.stat = stat;
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
	 * 入库时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}
	/**
	 * 入库时间.
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	/**
	 * 售票人.
	 */
	@Column(name = "SALE_USER_ID", length = 50)
	public String getSaleUserId() {
		return this.saleUserId;
	}
	/**
	 * 售票人.
	 */
	public void setSaleUserId(String saleUserId) {
		this.saleUserId = saleUserId;
	}

	/**
	 * 售票时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SALE_OPE_TIME", length = 7)
	public Date getSaleOpeTime() {
		return this.saleOpeTime;
	}
	/**
	 * 售票时间.
	 */
	public void setSaleOpeTime(Date saleOpeTime) {
		this.saleOpeTime = saleOpeTime;
	}

	/**
	 * 库房编号(网点编号或空为票务中心).
	 */
	@Column(name = "STORE_ID", length = 50)
	public String getStoreId() {
		return this.storeId;
	}
	/**
	 * 库房编号(网点编号或空为票务中心).
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * 出库时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELIVERY_TIME", length = 7)
	public Date getDeliveryTime() {
		return this.deliveryTime;
	}
	/**
	 * 出库时间.
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * 签收时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SIGN_TIME", length = 7)
	public Date getSignTime() {
		return this.signTime;
	}
	/**
	 * 签收时间.
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

}
