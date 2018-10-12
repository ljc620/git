package com.tbims.db.entity;
// Generated 2017-9-4 16:19:59 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 阶梯票价表
 */
@Entity
@Table(name = "SYS_TICKET_TYPE_PRICE", schema = "TBIMS")
public class SysTicketTypePrice implements java.io.Serializable {

	/** 
	 * 主键
	 */
	private String priceId;

	/** 
	 * 票种代码
	 */
	private String ticketTypeId;

	/** 
	 * 开始张数
	 */
	private Long startNo;

	/** 
	 * 结束张数
	 */
	private Long endNo;

	/** 
	 * 票价
	 */
	private Long price;

	/** 
	 * 创建时间
	 */
	private Date createTime;

	/** 
	 * 操作人
	 */
	private String createUserId;

	public SysTicketTypePrice() {
	}

	public SysTicketTypePrice(String priceId) {
		this.priceId = priceId;
	}

	public SysTicketTypePrice(String priceId, String ticketTypeId, Long startNo, Long endNo, Long price, Date createTime, String createUserId) {
		this.priceId = priceId;
		this.ticketTypeId = ticketTypeId;
		this.startNo = startNo;
		this.endNo = endNo;
		this.price = price;
		this.createTime = createTime;
		this.createUserId = createUserId;
	}

	/** 
	 * 主键.
	 */
	@Id
	@Column(name = "PRICE_ID", unique = true, nullable = false, length = 60)
	public String getPriceId() {
		return this.priceId;
	}

	/** 
	 * 主键.
	 */
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

	/** 
	 * 票种代码.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	/** 
	 * 票种代码.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 开始张数.
	 */
	@Column(name = "START_NO", precision = 12, scale = 0)
	public Long getStartNo() {
		return this.startNo;
	}

	/** 
	 * 开始张数.
	 */
	public void setStartNo(Long startNo) {
		this.startNo = startNo;
	}

	/** 
	 * 结束张数.
	 */
	@Column(name = "END_NO", precision = 12, scale = 0)
	public Long getEndNo() {
		return this.endNo;
	}

	/** 
	 * 结束张数.
	 */
	public void setEndNo(Long endNo) {
		this.endNo = endNo;
	}

	/** 
	 * 票价.
	 */
	@Column(name = "PRICE", precision = 4, scale = 0)
	public Long getPrice() {
		return this.price;
	}

	/** 
	 * 票价.
	 */
	public void setPrice(Long price) {
		this.price = price;
	}

	/** 
	 * 创建时间.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	/** 
	 * 创建时间.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "CREATE_USER_ID", length = 60)
	public String getCreateUserId() {
		return this.createUserId;
	}

	/** 
	 * 操作人.
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

}
