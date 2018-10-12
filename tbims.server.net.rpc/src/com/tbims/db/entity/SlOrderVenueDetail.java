package com.tbims.db.entity;
// Generated 2017-7-20 8:47:55 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 门票场馆明细表
 */
@Entity
@Table(name = "SL_ORDER_VENUE_DETAIL", schema = "TBIMS")
public class SlOrderVenueDetail implements java.io.Serializable {

	/** 
	 * 销售明细表ID
	 */
	/** 
	* 场馆编号
	*/
	private SlOrderVenueDetailId id;

	/** 
	 * 剩余次数
	 */
	private Long remainTimes;

	public SlOrderVenueDetail() {
	}

	public SlOrderVenueDetail(SlOrderVenueDetailId id) {
		this.id = id;
	}
	public SlOrderVenueDetail(SlOrderVenueDetailId id, Long remainTimes) {
		this.id = id;
		this.remainTimes = remainTimes;
	}

	/** 
	 * 销售明细表ID.
	 */
	/** 
	* 场馆编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orderDetailId", column = @Column(name = "ORDER_DETAIL_ID", nullable = false, length = 60)),
			@AttributeOverride(name = "venueId", column = @Column(name = "VENUE_ID", nullable = false, precision = 6, scale = 0))})
	public SlOrderVenueDetailId getId() {
		return this.id;
	}
	/** 
	 * 销售明细表ID.
	 */
	/** 
	* 场馆编号.
	*/
	public void setId(SlOrderVenueDetailId id) {
		this.id = id;
	}

	/** 
	 * 剩余次数.
	 */
	@Column(name = "REMAIN_TIMES", precision = 3, scale = 0)
	public Long getRemainTimes() {
		return this.remainTimes;
	}
	/** 
	 * 剩余次数.
	 */
	public void setRemainTimes(Long remainTimes) {
		this.remainTimes = remainTimes;
	}

}
