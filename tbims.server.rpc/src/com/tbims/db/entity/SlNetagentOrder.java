package com.tbims.db.entity;
// Generated 2017-7-15 14:20:42 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 网络代理订单表
 */
@Entity
@Table(name = "SL_NETAGENT_ORDER", schema = "TBIMS")
public class SlNetagentOrder implements java.io.Serializable {

	/** 
	 * 按销售类型等规则
	 */
	private String orderId;

	/** 
	 * 第三方单号
	 */
	private String thirdOrderNum;

	/** 
	 * 组织机构代码
	 */
	private String orgId;

	/** 
	 * 取票码
	 */
	private String ticketServiceCode;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public SlNetagentOrder() {
	}

	public SlNetagentOrder(String orderId) {
		this.orderId = orderId;
	}
	public SlNetagentOrder(String orderId, String thirdOrderNum, String orgId, String ticketServiceCode, String opeUserId, Date opeTime) {
		this.orderId = orderId;
		this.thirdOrderNum = thirdOrderNum;
		this.orgId = orgId;
		this.ticketServiceCode = ticketServiceCode;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 按销售类型等规则.
	 */
	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false, length = 50)
	public String getOrderId() {
		return this.orderId;
	}
	/** 
	 * 按销售类型等规则.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** 
	 * 第三方单号.
	 */
	@Column(name = "THIRD_ORDER_NUM", length = 60)
	public String getThirdOrderNum() {
		return this.thirdOrderNum;
	}
	/** 
	 * 第三方单号.
	 */
	public void setThirdOrderNum(String thirdOrderNum) {
		this.thirdOrderNum = thirdOrderNum;
	}

	/** 
	 * 组织机构代码.
	 */
	@Column(name = "ORG_ID", length = 20)
	public String getOrgId() {
		return this.orgId;
	}
	/** 
	 * 组织机构代码.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * 取票码.
	 */
	@Column(name = "TICKET_SERVICE_CODE", length = 100)
	public String getTicketServiceCode() {
		return this.ticketServiceCode;
	}
	/** 
	 * 取票码.
	 */
	public void setTicketServiceCode(String ticketServiceCode) {
		this.ticketServiceCode = ticketServiceCode;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}
	/** 
	 * 操作人.
	 */
	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/** 
	 * 操作时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
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
