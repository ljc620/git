package com.tbims.entity;
// Generated 2017-7-23 17:23:20 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 网点库存表
 */
@Entity
@Table(name = "STR_OUTLET_INFO", schema = "TBIMS")
public class StrOutletInfo implements java.io.Serializable {

	/** 
	 * 网点编号
	 */
	/** 
	* 票种编号
	*/
	private StrOutletInfoId id;

	/** 
	 * 库存张数
	 */
	private Long strTicketNum;

	/** 
	 * 更新人
	 */
	private String lastUpdUser;

	/** 
	 * 更新时间
	 */
	private Date lastUpdTime;

	public StrOutletInfo() {
	}

	public StrOutletInfo(StrOutletInfoId id) {
		this.id = id;
	}
	public StrOutletInfo(StrOutletInfoId id, Long strTicketNum, String lastUpdUser, Date lastUpdTime) {
		this.id = id;
		this.strTicketNum = strTicketNum;
		this.lastUpdUser = lastUpdUser;
		this.lastUpdTime = lastUpdTime;
	}

	/** 
	 * 网点编号.
	 */
	/** 
	* 票种编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "outletId", column = @Column(name = "OUTLET_ID", nullable = false, precision = 6, scale = 0)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3))})
	public StrOutletInfoId getId() {
		return this.id;
	}
	/** 
	 * 网点编号.
	 */
	/** 
	* 票种编号.
	*/
	public void setId(StrOutletInfoId id) {
		this.id = id;
	}

	/** 
	 * 库存张数.
	 */
	@Column(name = "STR_TICKET_NUM", precision = 12, scale = 0)
	public Long getStrTicketNum() {
		return this.strTicketNum;
	}
	/** 
	 * 库存张数.
	 */
	public void setStrTicketNum(Long strTicketNum) {
		this.strTicketNum = strTicketNum;
	}

	/** 
	 * 更新人.
	 */
	@Column(name = "LAST_UPD_USER", length = 60)
	public String getLastUpdUser() {
		return this.lastUpdUser;
	}
	/** 
	 * 更新人.
	 */
	public void setLastUpdUser(String lastUpdUser) {
		this.lastUpdUser = lastUpdUser;
	}

	/** 
	 * 更新时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPD_TIME", length = 7)
	public Date getLastUpdTime() {
		return this.lastUpdTime;
	}
	/** 
	 * 更新时间.
	 */
	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

}
