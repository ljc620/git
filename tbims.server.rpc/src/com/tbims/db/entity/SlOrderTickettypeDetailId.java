package com.tbims.db.entity;
// Generated 2017-7-8 17:53:01 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 销售单票种明细表
 */
@Embeddable
public class SlOrderTickettypeDetailId implements java.io.Serializable {

	/** 
	 * 销售单号,按销售类型等规则
	 */
	private String orderId;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	public SlOrderTickettypeDetailId() {
	}

	public SlOrderTickettypeDetailId(String orderId, String ticketTypeId) {
		this.orderId = orderId;
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 销售单号,按销售类型等规则.
	 */
	@Column(name = "ORDER_ID", nullable = false, length = 50)
	public String getOrderId() {
		return this.orderId;
	}
	/** 
	 * 销售单号,按销售类型等规则.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}
	/** 
	 * 票种编号.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SlOrderTickettypeDetailId))
			return false;
		SlOrderTickettypeDetailId castOther = (SlOrderTickettypeDetailId) other;

		return ((this.getOrderId() == castOther.getOrderId()) || (this.getOrderId() != null && castOther.getOrderId() != null && this.getOrderId().equals(castOther.getOrderId())))
				&& ((this.getTicketTypeId() == castOther.getTicketTypeId()) || (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null && this.getTicketTypeId().equals(castOther.getTicketTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOrderId() == null ? 0 : this.getOrderId().hashCode());
		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		return result;
	}

}
