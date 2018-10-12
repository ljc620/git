package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateTimeSerializer;

/**
 * 
* Title: 配送申请<br/>
* Description: 
* @ClassName: DeliveryApplyBean
* @author ly
* @date 2017年6月19日 下午6:28:08
*
 */
public class DeliveryApplyBean {
	/** 
	 * 申请编号.
	 */
	private String applyId;

	/** 
	 * 申请人.
	 */
	private String applyUserId;
	
	/**
	 * 申请人姓名
	 */
	private String applyUserName;

	/** 
	 * 申请时间.
	 */
	private Date applyTime;

	/** 
	 * 网点编号.
	 */
	private Long outletId;
	
	/**
	 * 网点名称
	 */
	private String outletName;
	
	/**
	 * 网点类型
	 */
	private String outletType;
	
	/** 
	 * 机构名称.
	 */
	private String orgName;
	/** 
	 * 申请配送时间.
	 */
	private Date applyDeliveryTime;

	/** 
	 * 审核人.
	 */
	private String examUserId;
	
	/**
	 * 审核人姓名
	 */
	private String examUserName;

	/** 
	 * 审核时间.
	 */
	private Date examTime;

	/** 
	 * 审核状态(0待审核1已审核2已配送3已接收).
	 */
	private String examStat;

	/** 
	 * 出库人编号.
	 */
	private String deliveryUserId;
	
	/**
	 * 出库人姓名
	 */
	private String deliveryUserName;

	/** 
	 * 出库时间.
	 */
	private Date deliveryTime;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getApplyDeliveryTime() {
		return applyDeliveryTime;
	}

	public void setApplyDeliveryTime(Date applyDeliveryTime) {
		this.applyDeliveryTime = applyDeliveryTime;
	}

	public String getExamUserId() {
		return examUserId;
	}

	public void setExamUserId(String examUserId) {
		this.examUserId = examUserId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	public String getExamStat() {
		return examStat;
	}

	public void setExamStat(String examStat) {
		this.examStat = examStat;
	}

	public String getDeliveryUserId() {
		return deliveryUserId;
	}

	public void setDeliveryUserId(String deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOutletType() {
		return outletType;
	}

	public void setOutletType(String outletType) {
		this.outletType = outletType;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getExamUserName() {
		return examUserName;
	}

	public void setExamUserName(String examUserName) {
		this.examUserName = examUserName;
	}

	public String getDeliveryUserName() {
		return deliveryUserName;
	}

	public void setDeliveryUserName(String deliveryUserName) {
		this.deliveryUserName = deliveryUserName;
	}

}
