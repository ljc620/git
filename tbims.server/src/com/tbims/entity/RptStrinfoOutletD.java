package com.tbims.entity;
// Generated 2017-7-11 9:37:01 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 每日网点库存汇总表
 */
@Entity
@Table(name = "RPT_STRINFO_OUTLET_D", schema = "TBIMS")
public class RptStrinfoOutletD implements java.io.Serializable {

	/** 
	 * 统计日期
	 */
	/** 
	* 网点编号
	*/
	/** 
	* 票种
	*/
	private RptStrinfoOutletDId id;

	/** 
	 * 上日库存张数
	 */
	private Long strLastTicketNum;

	/** 
	 * 入库张数
	 */
	private Long inTicketNum;

	/** 
	 * 售票张数（包括换票）
	 */
	private Long saleTicketNum;

	/** 
	 * 坏票废票张数
	 */
	private Long uselessTicketNum;
	
	/** 
	 * 补票废票张数
	 */
	private Long supplyTicketNum;

	/** 
	 * 库存张数
	 */
	private Long strTicketNum;

	public RptStrinfoOutletD() {
	}

	public RptStrinfoOutletD(RptStrinfoOutletDId id) {
		this.id = id;
	}
	public RptStrinfoOutletD(RptStrinfoOutletDId id, Long strLastTicketNum, Long inTicketNum, Long saleTicketNum, Long uselessTicketNum, Long strTicketNum) {
		this.id = id;
		this.strLastTicketNum = strLastTicketNum;
		this.inTicketNum = inTicketNum;
		this.saleTicketNum = saleTicketNum;
		this.uselessTicketNum = uselessTicketNum;
		this.strTicketNum = strTicketNum;
	}

	/** 
	 * 统计日期.
	 */
	/** 
	* 网点编号.
	*/
	/** 
	* 票种.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rptDate", column = @Column(name = "RPT_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "outletId", column = @Column(name = "OUTLET_ID", nullable = false, precision = 6, scale = 0)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3))})
	public RptStrinfoOutletDId getId() {
		return this.id;
	}
	/** 
	 * 统计日期.
	 */
	/** 
	* 网点编号.
	*/
	/** 
	* 票种.
	*/
	public void setId(RptStrinfoOutletDId id) {
		this.id = id;
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
	 * 售票张数（包括换票）.
	 */
	@Column(name = "SALE_TICKET_NUM", precision = 12, scale = 0)
	public Long getSaleTicketNum() {
		return this.saleTicketNum;
	}
	/** 
	 * 售票张数（包括换票）.
	 */
	public void setSaleTicketNum(Long saleTicketNum) {
		this.saleTicketNum = saleTicketNum;
	}

	/** 
	 * 坏票废票张数.
	 */
	@Column(name = "USELESS_TICKET_NUM", precision = 12, scale = 0)
	public Long getUselessTicketNum() {
		return this.uselessTicketNum;
	}
	/** 
	 * 坏票废票张数.
	 */
	public void setUselessTicketNum(Long uselessTicketNum) {
		this.uselessTicketNum = uselessTicketNum;
	}

	/** 
	 * 补票废票张数.
	 */
	@Column(name = "SUPPLY_TICKET_NUM", precision = 12, scale = 0)
	public Long getSupplyTicketNum() {
		return supplyTicketNum;
	}

	/** 
	 * 补票废票张数.
	 */
	public void setSupplyTicketNum(Long supplyTicketNum) {
		this.supplyTicketNum = supplyTicketNum;
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
