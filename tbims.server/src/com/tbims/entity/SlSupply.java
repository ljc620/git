package com.tbims.entity;
// Generated 2017-6-27 13:02:50 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 补票表
 */
@Entity
@Table(name = "SL_SUPPLY", schema = "TBIMS")
public class SlSupply implements java.io.Serializable {

	/** 
	 * 销售单号
	 */
	private String supplyId;

	/** 
	 * 新票号
	 */
	private Long newTicketId;

	/** 
	 * 旧票号
	 */
	private Long oldTicketId;

	/** 
	 * 新票价
	 */
	private Long newPrice;

	/** 
	 * 旧票价
	 */
	private Long oldPrice;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public SlSupply() {
	}

	public SlSupply(String supplyId) {
		this.supplyId = supplyId;
	}
	public SlSupply(String supplyId, Long newTicketId, Long oldTicketId, Long newPrice, Long oldPrice, String ticketTypeId, String opeUserId, Date opeTime) {
		this.supplyId = supplyId;
		this.newTicketId = newTicketId;
		this.oldTicketId = oldTicketId;
		this.newPrice = newPrice;
		this.oldPrice = oldPrice;
		this.ticketTypeId = ticketTypeId;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 销售单号.
	 */
	@Id
	@Column(name = "SUPPLY_ID", unique = true, nullable = false, length = 60)
	public String getSupplyId() {
		return this.supplyId;
	}
	/** 
	 * 销售单号.
	 */
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	/** 
	 * 新票号.
	 */
	@Column(name = "NEW_TICKET_ID", precision = 10, scale = 0)
	public Long getNewTicketId() {
		return this.newTicketId;
	}
	/** 
	 * 新票号.
	 */
	public void setNewTicketId(Long newTicketId) {
		this.newTicketId = newTicketId;
	}

	/** 
	 * 旧票号.
	 */
	@Column(name = "OLD_TICKET_ID", precision = 10, scale = 0)
	public Long getOldTicketId() {
		return this.oldTicketId;
	}
	/** 
	 * 旧票号.
	 */
	public void setOldTicketId(Long oldTicketId) {
		this.oldTicketId = oldTicketId;
	}

	/** 
	 * 新票价.
	 */
	@Column(name = "NEW_PRICE", precision = 5, scale = 0)
	public Long getNewPrice() {
		return this.newPrice;
	}
	/** 
	 * 新票价.
	 */
	public void setNewPrice(Long newPrice) {
		this.newPrice = newPrice;
	}

	/** 
	 * 旧票价.
	 */
	@Column(name = "OLD_PRICE", precision = 5, scale = 0)
	public Long getOldPrice() {
		return this.oldPrice;
	}
	/** 
	 * 旧票价.
	 */
	public void setOldPrice(Long oldPrice) {
		this.oldPrice = oldPrice;
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

}
