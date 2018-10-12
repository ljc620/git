package com.tbims.entity;
// Generated 2017-7-11 9:37:01 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 每日检票信息统计
 */
@Entity
@Table(name = "RPT_CHECKTICKETINFO_D", schema = "TBIMS")
public class RptCheckticketinfoD implements java.io.Serializable {

	/** 
	 * 统计日期
	 */
	/** 
	* 终端编号
	*/
	private RptCheckticketinfoDId id;

	/** 
	 * 区域编号
	 */
	private Long regionId;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	/** 
	 * 检票数量
	 */
	private Long checkTicketNum;

	public RptCheckticketinfoD() {
	}

	public RptCheckticketinfoD(RptCheckticketinfoDId id) {
		this.id = id;
	}
	public RptCheckticketinfoD(RptCheckticketinfoDId id, Long regionId, String ticketTypeId, Long checkTicketNum) {
		this.id = id;
		this.regionId = regionId;
		this.ticketTypeId = ticketTypeId;
		this.checkTicketNum = checkTicketNum;
	}

	/** 
	 * 统计日期.
	 */
	/** 
	* 终端编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rptDate", column = @Column(name = "RPT_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "clientId", column = @Column(name = "CLIENT_ID", nullable = false, precision = 10, scale = 0))})
	public RptCheckticketinfoDId getId() {
		return this.id;
	}
	/** 
	 * 统计日期.
	 */
	/** 
	* 终端编号.
	*/
	public void setId(RptCheckticketinfoDId id) {
		this.id = id;
	}

	/** 
	 * 区域编号.
	 */
	@Column(name = "REGION_ID", precision = 6, scale = 0)
	public Long getRegionId() {
		return this.regionId;
	}
	/** 
	 * 区域编号.
	 */
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
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
	 * 检票数量.
	 */
	@Column(name = "CHECK_TICKET_NUM", precision = 12, scale = 0)
	public Long getCheckTicketNum() {
		return this.checkTicketNum;
	}
	/** 
	 * 检票数量.
	 */
	public void setCheckTicketNum(Long checkTicketNum) {
		this.checkTicketNum = checkTicketNum;
	}

}
