package com.tbims.entity;
// Generated 2017-7-11 9:37:01 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 每日票务中心库存汇总表
 */
@Entity
@Table(name = "RPT_STRINFO_D", schema = "TBIMS")
public class RptStrinfoD implements java.io.Serializable {

	/** 
	 * 统计日期
	 */
	
	/** 
	* 票种编号
	*/
	private RptStrinfoDId id;

	/** 
	 * 上日库存箱数
	 */
	private Long strLastChestNum;

	/** 
	 * 上日库存张数
	 */
	private Long strLastTicketNum;

	/** 
	 * 入库箱数
	 */
	private Long inChestNum;

	/** 
	 * 入库张数
	 */
	private Long inTicketNum;

	/** 
	 * 出库箱数
	 */
	private Long outChestNum;

	/** 
	 * 出库张数
	 */
	private Long outTicketNum;

	/** 
	 * 库存箱数
	 */
	private Long strChestNum;

	/** 
	 * 库存张数
	 */
	private Long strTicketNum;

	public RptStrinfoD() {
	}

	public RptStrinfoD(RptStrinfoDId id) {
		this.id = id;
	}
	public RptStrinfoD(RptStrinfoDId id, Long strLastChestNum, Long strLastTicketNum, Long inChestNum, Long inTicketNum, Long outChestNum, Long outTicketNum, Long strChestNum, Long strTicketNum) {
		this.id = id;
		this.strLastChestNum = strLastChestNum;
		this.strLastTicketNum = strLastTicketNum;
		this.inChestNum = inChestNum;
		this.inTicketNum = inTicketNum;
		this.outChestNum = outChestNum;
		this.outTicketNum = outTicketNum;
		this.strChestNum = strChestNum;
		this.strTicketNum = strTicketNum;
	
	}


	
	/** 
	* 票种编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rptDate", column = @Column(name = "RPT_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3))})
	public RptStrinfoDId getId() {
		return this.id;
	}
	
	/** 
	* 票种编号.
	*/
	public void setId(RptStrinfoDId id) {
		this.id = id;
	}

	/** 
	 * 上日库存箱数.
	 */
	@Column(name = "STR_LAST_CHEST_NUM", precision = 12, scale = 0)
	public Long getStrLastChestNum() {
		return this.strLastChestNum;
	}
	/** 
	 * 上日库存箱数.
	 */
	public void setStrLastChestNum(Long strLastChestNum) {
		this.strLastChestNum = strLastChestNum;
	}

	/** 
	 * 上日库存张数.
	 */
	@Column(name = "STR_LAST_TICKET_NUM", precision = 12, scale = 0)
	public Long getStrLastTicketNum() {
		return this.strLastTicketNum;
	}
	/** 
	 * 上日库存张数.
	 */
	public void setStrLastTicketNum(Long strLastTicketNum) {
		this.strLastTicketNum = strLastTicketNum;
	}

	/** 
	 * 入库箱数.
	 */
	@Column(name = "IN_CHEST_NUM", precision = 12, scale = 0)
	public Long getInChestNum() {
		return this.inChestNum;
	}
	/** 
	 * 入库箱数.
	 */
	public void setInChestNum(Long inChestNum) {
		this.inChestNum = inChestNum;
	}

	/** 
	 * 入库张数.
	 */
	@Column(name = "IN_TICKET_NUM", precision = 12, scale = 0)
	public Long getInTicketNum() {
		return this.inTicketNum;
	}
	/** 
	 * 入库张数.
	 */
	public void setInTicketNum(Long inTicketNum) {
		this.inTicketNum = inTicketNum;
	}

	/** 
	 * 出库箱数.
	 */
	@Column(name = "OUT_CHEST_NUM", precision = 12, scale = 0)
	public Long getOutChestNum() {
		return this.outChestNum;
	}
	/** 
	 * 出库箱数.
	 */
	public void setOutChestNum(Long outChestNum) {
		this.outChestNum = outChestNum;
	}

	/** 
	 * 出库张数.
	 */
	@Column(name = "OUT_TICKET_NUM", precision = 12, scale = 0)
	public Long getOutTicketNum() {
		return this.outTicketNum;
	}
	/** 
	 * 出库张数.
	 */
	public void setOutTicketNum(Long outTicketNum) {
		this.outTicketNum = outTicketNum;
	}

	/** 
	 * 库存箱数.
	 */
	@Column(name = "STR_CHEST_NUM", precision = 12, scale = 0)
	public Long getStrChestNum() {
		return this.strChestNum;
	}
	/** 
	 * 库存箱数.
	 */
	public void setStrChestNum(Long strChestNum) {
		this.strChestNum = strChestNum;
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

}
