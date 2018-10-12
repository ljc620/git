package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 票种场馆表
 */
@Embeddable
public class SysTicketTypeVenueId implements java.io.Serializable {

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	/** 
	 * 场馆编号.
	 */
	private int venueId;

	public SysTicketTypeVenueId() {
	}

	public SysTicketTypeVenueId(String ticketTypeId, int venueId) {
		this.ticketTypeId = ticketTypeId;
		this.venueId = venueId;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 场馆编号.
	 */
	@Column(name = "VENUE_ID", nullable = false, precision = 6, scale = 0)
	public int getVenueId() {
		return this.venueId;
	}

	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysTicketTypeVenueId))
			return false;
		SysTicketTypeVenueId castOther = (SysTicketTypeVenueId) other;

		return ((this.getTicketTypeId() == castOther.getTicketTypeId()) || (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null && this.getTicketTypeId().equals(castOther.getTicketTypeId())))
				&& (this.getVenueId() == castOther.getVenueId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		result = 37 * result + this.getVenueId();
		return result;
	}

}
