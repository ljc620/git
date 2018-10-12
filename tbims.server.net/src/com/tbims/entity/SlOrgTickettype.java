package com.tbims.entity;
// Generated 2017-11-6 14:28:08 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 机构可销售票种表
 */
@Entity
@Table(name = "SL_ORG_TICKETTYPE", schema = "TBIMS")
public class SlOrgTickettype implements java.io.Serializable {

	/** 
	 * 机构代码
	 */
	/** 
	* 票种
	*/
	private SlOrgTickettypeId id;

	public SlOrgTickettype() {
	}

	public SlOrgTickettype(SlOrgTickettypeId id) {
		this.id = id;
	}

	/** 
	 * 机构代码.
	 */
	/** 
	* 票种.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orgId", column = @Column(name = "ORG_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)) })
	public SlOrgTickettypeId getId() {
		return this.id;
	}

	/** 
	 * 机构代码.
	 */
	/** 
	* 票种.
	*/
	public void setId(SlOrgTickettypeId id) {
		this.id = id;
	}

}
