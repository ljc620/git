package com.tbims.entity;
// Generated 2017-6-19 19:09:15 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 配送申请表
 */
@Entity
@Table(name = "STR_DELIVERY_APPLY", schema = "TBIMS")
public class StrDeliveryApply implements java.io.Serializable {

	/** 
	 * 申请编号
	 */
	private String applyId;

	/** 
	 * 申请人
	 */
	private String applyUserId;

	/** 
	 * 申请时间
	 */
	private Date applyTime;

	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 申请配送时间
	 */
	private Date applyDeliveryTime;

	/** 
	 * 审核人
	 */
	private String examUserId;

	/** 
	 * 审核时间
	 */
	private Date examTime;

	/** 
	 * 审核状态(0待审核1已审核2已配送3已接收4已拒收5已退回)
	 */
	private String examStat;

	/** 
	 * 出库人编号
	 */
	private String deliveryUserId;

	/** 
	 * 出库时间
	 */
	private Date deliveryTime;
	
	/** 
	 * 签收人编号
	 */
	private String signUserId;

	/** 
	 * 签收时间
	 */
	private Date signTime;

	private List<StrDeliveryDetail> strDeliveryDetails = new ArrayList<StrDeliveryDetail>(0);

	private List<StrDeliveryApplyDetail> strDeliveryApplyDetails = new ArrayList<StrDeliveryApplyDetail>(0);

	public StrDeliveryApply() {
	}

	public StrDeliveryApply(String applyId) {
		this.applyId = applyId;
	}
	public StrDeliveryApply(String applyId, String applyUserId, Date applyTime, Long outletId, Date applyDeliveryTime, String examUserId, Date examTime, String examStat, String deliveryUserId, Date deliveryTime, String signUserId,Date signTime,List<StrDeliveryDetail> strDeliveryDetails, List<StrDeliveryApplyDetail> strDeliveryApplyDetails) {
		this.applyId = applyId;
		this.applyUserId = applyUserId;
		this.applyTime = applyTime;
		this.outletId = outletId;
		this.applyDeliveryTime = applyDeliveryTime;
		this.examUserId = examUserId;
		this.examTime = examTime;
		this.examStat = examStat;
		this.deliveryUserId = deliveryUserId;
		this.deliveryTime = deliveryTime;
		this.signUserId = signUserId;
		this.signTime = signTime;
		this.strDeliveryDetails = strDeliveryDetails;
		this.strDeliveryApplyDetails = strDeliveryApplyDetails;
	}

	/** 
	 * 申请编号.
	 */
	@Id
	@Column(name = "APPLY_ID", unique = true, nullable = false, length = 60)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	/** 
	 * 申请人.
	 */
	@Column(name = "APPLY_USER_ID", length = 50)
	public String getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	/** 
	 * 申请时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_TIME", length = 7)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/** 
	 * 网点编号.
	 */
	@Column(name = "OUTLET_ID", length = 50)
	public Long getOutletId() {
		return this.outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/** 
	 * 申请配送时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_DELIVERY_TIME", length = 7)
	public Date getApplyDeliveryTime() {
		return this.applyDeliveryTime;
	}

	public void setApplyDeliveryTime(Date applyDeliveryTime) {
		this.applyDeliveryTime = applyDeliveryTime;
	}

	/** 
	 * 审核人.
	 */
	@Column(name = "EXAM_USER_ID", length = 50)
	public String getExamUserId() {
		return this.examUserId;
	}

	public void setExamUserId(String examUserId) {
		this.examUserId = examUserId;
	}

	/** 
	 * 审核时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXAM_TIME", length = 7)
	public Date getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	/** 
	 * 审核状态(0待审核1已审核2已配送3已接收).
	 */
	@Column(name = "EXAM_STAT", length = 1)
	public String getExamStat() {
		return this.examStat;
	}

	public void setExamStat(String examStat) {
		this.examStat = examStat;
	}

	/** 
	 * 出库人编号.
	 */
	@Column(name = "DELIVERY_USER_ID", length = 50)
	public String getDeliveryUserId() {
		return this.deliveryUserId;
	}

	public void setDeliveryUserId(String deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}

	/** 
	 * 出库时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELIVERY_TIME", length = 7)
	public Date getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/** 
	 * 签收人编号.
	 */
	@Column(name = "SIGN_USER_ID", length = 50)
	public String getSignUserId() {
		return signUserId;
	}

	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}

	/** 
	 * 签收时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SIGN_TIME", length = 7)
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.ALL}, mappedBy = "strDeliveryApply")
	public List<StrDeliveryDetail> getStrDeliveryDetails() {
		return this.strDeliveryDetails;
	}

	public void setStrDeliveryDetails(List<StrDeliveryDetail> strDeliveryDetails) {
		this.strDeliveryDetails = strDeliveryDetails;
	}

	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.ALL},  mappedBy = "strDeliveryApply")
	public List<StrDeliveryApplyDetail> getStrDeliveryApplyDetails() {
		return this.strDeliveryApplyDetails;
	}

	public void setStrDeliveryApplyDetails(List<StrDeliveryApplyDetail> strDeliveryApplyDetails) {
		this.strDeliveryApplyDetails = strDeliveryApplyDetails;
	}

}
