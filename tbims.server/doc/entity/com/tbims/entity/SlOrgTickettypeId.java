package com.tbims.entity;
// Generated 2017-11-6 14:28:08 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: ����������Ʊ�ֱ�
 */
@Embeddable
public class SlOrgTickettypeId implements java.io.Serializable {

	/** 
	 * ��������
	 */
	private String orgId;

	/** 
	 * Ʊ��
	 */
	private String ticketTypeId;

	public SlOrgTickettypeId() {
	}

	public SlOrgTickettypeId(String orgId, String ticketTypeId) {
		this.orgId = orgId;
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * ��������.
	 */
	@Column(name = "ORG_ID", nullable = false, length = 20)
	public String getOrgId() {
		return this.orgId;
	}

	/** 
	 * ��������.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * Ʊ��.
	 */
	@Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	/** 
	 * Ʊ��.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SlOrgTickettypeId))
			return false;
		SlOrgTickettypeId castOther = (SlOrgTickettypeId) other;

		return ((this.getOrgId() == castOther.getOrgId()) || (this.getOrgId() != null && castOther.getOrgId() != null
				&& this.getOrgId().equals(castOther.getOrgId())))
				&& ((this.getTicketTypeId() == castOther.getTicketTypeId())
						|| (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null
								&& this.getTicketTypeId().equals(castOther.getTicketTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOrgId() == null ? 0 : this.getOrgId().hashCode());
		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		return result;
	}

}
