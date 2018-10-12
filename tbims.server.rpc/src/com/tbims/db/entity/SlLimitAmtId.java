package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 额度余额表
 */
@Embeddable
public class SlLimitAmtId implements java.io.Serializable {

	/** 
	 * 机构编号.
	 */
	private String orgId;

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	public SlLimitAmtId() {
	}

	public SlLimitAmtId(String orgId, String ticketTypeId) {
		this.orgId = orgId;
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 机构编号.
	 */
	@Column(name = "ORG_ID", nullable = false, length = 50)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SlLimitAmtId))
			return false;
		SlLimitAmtId castOther = (SlLimitAmtId) other;

		return ((this.getOrgId() == castOther.getOrgId()) || (this.getOrgId() != null && castOther.getOrgId() != null && this.getOrgId().equals(castOther.getOrgId())))
				&& ((this.getTicketTypeId() == castOther.getTicketTypeId()) || (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null && this.getTicketTypeId().equals(castOther.getTicketTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOrgId() == null ? 0 : this.getOrgId().hashCode());
		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		return result;
	}

}
