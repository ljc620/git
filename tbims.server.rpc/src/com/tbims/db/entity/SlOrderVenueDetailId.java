package com.tbims.db.entity;
// Generated 2017-7-20 8:47:55 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 门票场馆明细表
 */
@Embeddable
public class SlOrderVenueDetailId implements java.io.Serializable {

	/** 
	 * 销售明细表ID
	 */
	private String orderDetailId;

	/** 
	 * 场馆编号
	 */
	private Long venueId;

	public SlOrderVenueDetailId() {
	}

	public SlOrderVenueDetailId(String orderDetailId, Long venueId) {
		this.orderDetailId = orderDetailId;
		this.venueId = venueId;
	}

	/** 
	 * 销售明细表ID.
	 */
	@Column(name = "ORDER_DETAIL_ID", nullable = false, length = 60)
	public String getOrderDetailId() {
		return this.orderDetailId;
	}
	/** 
	 * 销售明细表ID.
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	/** 
	 * 场馆编号.
	 */
	@Column(name = "VENUE_ID", nullable = false, precision = 6, scale = 0)
	public Long getVenueId() {
		return this.venueId;
	}
	/** 
	 * 场馆编号.
	 */
	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SlOrderVenueDetailId))
			return false;
		SlOrderVenueDetailId castOther = (SlOrderVenueDetailId) other;

		return ((this.getOrderDetailId() == castOther.getOrderDetailId()) || (this.getOrderDetailId() != null && castOther.getOrderDetailId() != null && this.getOrderDetailId().equals(castOther.getOrderDetailId())))
				&& ((this.getVenueId() == castOther.getVenueId()) || (this.getVenueId() != null && castOther.getVenueId() != null && this.getVenueId().equals(castOther.getVenueId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOrderDetailId() == null ? 0 : this.getOrderDetailId().hashCode());
		result = 37 * result + (getVenueId() == null ? 0 : this.getVenueId().hashCode());
		return result;
	}

}
