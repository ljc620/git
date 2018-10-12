package com.tbims.db.entity;
// Generated 2017-6-19 19:09:15 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity: 配送明细表
 */
@Entity
@Table(name = "STR_DELIVERY_DETAIL", schema = "TBIMS")
public class StrDeliveryDetail implements java.io.Serializable {

	/**
	 * 配送明细ID
	 */
	private String deliveryDetailId;

	/**
	 * 申请编号
	 */
	private StrDeliveryApply strDeliveryApply;

	/**
	 * 票种编号
	 */
	private String ticketTypeId;

	/**
	 * 箱号
	 */
	private String chestId;

	/**
	 * 票起号
	 */
	private Long beginNo;

	/**
	 * 票止号
	 */
	private Long endNo;

	public StrDeliveryDetail() {
	}

	public StrDeliveryDetail(String deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
	}
	public StrDeliveryDetail(String deliveryDetailId, StrDeliveryApply strDeliveryApply, String chestId, String ticketTypeId, Long beginNo, Long endNo) {
		this.deliveryDetailId = deliveryDetailId;
		this.strDeliveryApply = strDeliveryApply;
		this.chestId = chestId;
		this.ticketTypeId = ticketTypeId;
		this.beginNo = beginNo;
		this.endNo = endNo;
	}

	/**
	 * 配送明细ID.
	 */
	@Id
	@Column(name = "DELIVERY_DETAIL_ID", unique = true, nullable = false, length = 60)
	public String getDeliveryDetailId() {
		return this.deliveryDetailId;
	}

	public void setDeliveryDetailId(String deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
	}

	/**
	 * 申请编号.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLY_ID")
	public StrDeliveryApply getStrDeliveryApply() {
		return this.strDeliveryApply;
	}

	public void setStrDeliveryApply(StrDeliveryApply strDeliveryApply) {
		this.strDeliveryApply = strDeliveryApply;
	}

	/**
	 * 箱号.
	 */
	@Column(name = "CHEST_ID", precision = 10, scale = 0)
	public String getChestId() {
		return this.chestId;
	}

	public void setChestId(String chestId) {
		this.chestId = chestId;
	}

	/**
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/**
	 * 票起号.
	 */
	@Column(name = "BEGIN_NO", precision = 10, scale = 0)
	public Long getBeginNo() {
		return this.beginNo;
	}
	/**
	 * 票起号.
	 */
	public void setBeginNo(Long beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * 票止号.
	 */
	@Column(name = "END_NO", precision = 10, scale = 0)
	public Long getEndNo() {
		return this.endNo;
	}
	/**
	 * 票止号.
	 */
	public void setEndNo(Long endNo) {
		this.endNo = endNo;
	}

}
