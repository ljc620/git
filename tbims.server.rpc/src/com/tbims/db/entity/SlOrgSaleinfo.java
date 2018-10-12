package com.tbims.db.entity;
// Generated 2017-9-4 16:59:53 by Hibernate Tools 4.0.0

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
 * Entity: 机构售票信息表
 */
@Entity
@Table(name = "SL_ORG_SALEINFO", schema = "TBIMS")
public class SlOrgSaleinfo implements java.io.Serializable {

	/** 
	 * 组织机构代码
	 */
	/** 
	* 票种编号
	*/
	private SlOrgSaleinfoId id;

	/** 
	 * 销售总张数(签约社用)
	 */
	private Long saleTotalNum;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public SlOrgSaleinfo() {
	}

	public SlOrgSaleinfo(SlOrgSaleinfoId id) {
		this.id = id;
	}

	public SlOrgSaleinfo(SlOrgSaleinfoId id, Long saleTotalNum, Date opeTime) {
		this.id = id;
		this.saleTotalNum = saleTotalNum;
		this.opeTime = opeTime;
	}

	/** 
	 * 组织机构代码.
	 */
	/** 
	* 票种编号.
	*/
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "orgId", column = @Column(name = "ORG_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)) })
	public SlOrgSaleinfoId getId() {
		return this.id;
	}

	/** 
	 * 组织机构代码.
	 */
	/** 
	* 票种编号.
	*/
	public void setId(SlOrgSaleinfoId id) {
		this.id = id;
	}

	/** 
	 * 销售总张数(签约社用).
	 */
	@Column(name = "SALE_TOTAL_NUM", precision = 12, scale = 0)
	public Long getSaleTotalNum() {
		return this.saleTotalNum;
	}

	/** 
	 * 销售总张数(签约社用).
	 */
	public void setSaleTotalNum(Long saleTotalNum) {
		this.saleTotalNum = saleTotalNum;
	}

	/** 
	 * 操作时间.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}

	/** 
	 * 操作时间.
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

}
