package com.tbims.db.entity;
// Generated 2017-7-29 18:18:18 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 退款明细
 */
@Entity
@Table(name = "SL_REFUND_TICKET_DETAIL", schema = "TBIMS")
public class SlRefundTicketDetail implements java.io.Serializable {

	/** 
	 * 退款明细
	 */
	private String refundDetailId;

	/** 
	 * 退款单号
	 */
	private String refundId;

	/** 
	 * 门票类型(1-FRID、2-身份证、3-二维码)
	 */
	private String ticketClass;

	/** 
	 * 票号
	 */
	private Long ticketId;

	/** 
	 * 票据唯一号或字符串型票号
	 */
	private String ticketUid;

	/** 
	 * 身份证号码
	 */
	private String identtyId;

	/** 
	 * 退款金额
	 */
	private Long refundAmt;

	public SlRefundTicketDetail() {
	}

	public SlRefundTicketDetail(String refundDetailId) {
		this.refundDetailId = refundDetailId;
	}

	public SlRefundTicketDetail(String refundDetailId, String refundId, String ticketClass, Long ticketId, String ticketUid, String identtyId, Long refundAmt) {
		this.refundDetailId = refundDetailId;
		this.refundId = refundId;
		this.ticketClass = ticketClass;
		this.ticketId = ticketId;
		this.ticketUid = ticketUid;
		this.identtyId = identtyId;
		this.refundAmt = refundAmt;
	}

	/** 
	 * 退款明细.
	 */
	@Id
	@Column(name = "REFUND_DETAIL_ID", unique = true, nullable = false, length = 60)
	public String getRefundDetailId() {
		return this.refundDetailId;
	}

	/** 
	 * 退款明细.
	 */
	public void setRefundDetailId(String refundDetailId) {
		this.refundDetailId = refundDetailId;
	}

	/** 
	 * 退款单号.
	 */
	@Column(name = "REFUND_ID", length = 50)
	public String getRefundId() {
		return this.refundId;
	}

	/** 
	 * 退款单号.
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	/** 
	 * 门票类型(1-FRID、2-身份证、3-二维码).
	 */
	@Column(name = "TICKET_CLASS", length = 1)
	public String getTicketClass() {
		return this.ticketClass;
	}

	/** 
	 * 门票类型(1-FRID、2-身份证、3-二维码).
	 */
	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	/** 
	 * 票号.
	 */
	@Column(name = "TICKET_ID", precision = 15, scale = 0)
	public Long getTicketId() {
		return this.ticketId;
	}

	/** 
	 * 票号.
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/** 
	 * 票据唯一号或字符串型票号.
	 */
	@Column(name = "TICKET_UID", length = 50)
	public String getTicketUid() {
		return this.ticketUid;
	}

	/** 
	 * 票据唯一号或字符串型票号.
	 */
	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}

	/** 
	 * 身份证号码.
	 */
	@Column(name = "IDENTTY_ID", length = 18)
	public String getIdenttyId() {
		return this.identtyId;
	}

	/** 
	 * 身份证号码.
	 */
	public void setIdenttyId(String identtyId) {
		this.identtyId = identtyId;
	}

	/** 
	 * 退款金额.
	 */
	@Column(name = "REFUND_AMT", precision = 12, scale = 0)
	public Long getRefundAmt() {
		return this.refundAmt;
	}

	/** 
	 * 退款金额.
	 */
	public void setRefundAmt(Long refundAmt) {
		this.refundAmt = refundAmt;
	}

}
