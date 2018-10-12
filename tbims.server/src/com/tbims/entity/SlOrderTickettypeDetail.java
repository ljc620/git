package com.tbims.entity;
// Generated 2017-7-8 17:53:01 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 销售单票种明细表
 */
@Entity
@Table(name = "SL_ORDER_TICKETTYPE_DETAIL", schema = "TBIMS")
public class SlOrderTickettypeDetail implements java.io.Serializable {

	/** 
	 * 销售单号,按销售类型等规则
	 */
	/** 
	* 票种编号
	*/
	private SlOrderTickettypeDetailId id;

	/** 
	 * 销售张数
	 */
	private Long ticketCount;

	/** 
	 * 出票张数
	 */
	private Long ejectTicketCount;

	public SlOrderTickettypeDetail() {
	}

	public SlOrderTickettypeDetail(SlOrderTickettypeDetailId id) {
		this.id = id;
	}
	public SlOrderTickettypeDetail(SlOrderTickettypeDetailId id, Long ticketCount, Long ejectTicketCount) {
		this.id = id;
		this.ticketCount = ticketCount;
		this.ejectTicketCount = ejectTicketCount;
	}

	/** 
	 * 销售单号,按销售类型等规则.
	 */
	/** 
	* 票种编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orderId", column = @Column(name = "ORDER_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3))})
	public SlOrderTickettypeDetailId getId() {
		return this.id;
	}
	/** 
	 * 销售单号,按销售类型等规则.
	 */
	/** 
	* 票种编号.
	*/
	public void setId(SlOrderTickettypeDetailId id) {
		this.id = id;
	}

	/** 
	 * 销售张数.
	 */
	@Column(name = "TICKET_COUNT", precision = 6, scale = 0)
	public Long getTicketCount() {
		return this.ticketCount;
	}
	/** 
	 * 销售张数.
	 */
	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}

	/** 
	 * 出票张数.
	 */
	@Column(name = "EJECT_TICKET_COUNT", precision = 6, scale = 0)
	public Long getEjectTicketCount() {
		return this.ejectTicketCount;
	}
	/** 
	 * 出票张数.
	 */
	public void setEjectTicketCount(Long ejectTicketCount) {
		this.ejectTicketCount = ejectTicketCount;
	}

}
