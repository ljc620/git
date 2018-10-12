package com.tbims.entity;
// Generated 2017-8-9 18:52:19 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 支付方式表
 */
@Entity
@Table(name = "SL_PAY_TYPE", schema = "TBIMS")
public class SlPayType implements java.io.Serializable {

	/** 
	 * 订单支付方式id
	 */
	private String payTypeId;

	/** 
	 * 销售单号
	 */
	private String orderId;

	/** 
	 * 支付方式(01-现金,02-POS付款,03-微信，04-支付宝，05-代理)
	 */
	private String payType;

	/** 
	 * 金额
	 */
	private Long amt;

	/** 
	 * 第三方支付单号
	 */
	private String payId;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	/** 
	 * 付款码
	 */
	private String paymentCode;

	/** 
	 * 请求参数
	 */
	private String requestParam;

	/** 
	 * 响应参数
	 */
	private String responseParams;

	/** 
	 * 银行单号
	 */
	private String bankId;

	public SlPayType() {
	}

	public SlPayType(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	public SlPayType(String payTypeId, String orderId, String payType, Long amt, String payId, Date versionNo, String paymentCode, String requestParam, String responseParams, String bankId) {
		this.payTypeId = payTypeId;
		this.orderId = orderId;
		this.payType = payType;
		this.amt = amt;
		this.payId = payId;
		this.versionNo = versionNo;
		this.paymentCode = paymentCode;
		this.requestParam = requestParam;
		this.responseParams = responseParams;
		this.bankId = bankId;
	}

	/** 
	 * 订单支付方式id.
	 */
	@Id
	@Column(name = "PAY_TYPE_ID", unique = true, nullable = false, length = 60)
	public String getPayTypeId() {
		return this.payTypeId;
	}

	/** 
	 * 订单支付方式id.
	 */
	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	/** 
	 * 销售单号.
	 */
	@Column(name = "ORDER_ID", length = 50)
	public String getOrderId() {
		return this.orderId;
	}

	/** 
	 * 销售单号.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** 
	 * 支付方式(01-现金,02-POS付款,03-微信，04-支付宝，05-代理).
	 */
	@Column(name = "PAY_TYPE", length = 2)
	public String getPayType() {
		return this.payType;
	}

	/** 
	 * 支付方式(01-现金,02-POS付款,03-微信，04-支付宝，05-代理).
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/** 
	 * 金额.
	 */
	@Column(name = "AMT", precision = 12, scale = 0)
	public Long getAmt() {
		return this.amt;
	}

	/** 
	 * 金额.
	 */
	public void setAmt(Long amt) {
		this.amt = amt;
	}

	/** 
	 * 第三方支付单号.
	 */
	@Column(name = "PAY_ID", length = 60)
	public String getPayId() {
		return this.payId;
	}

	/** 
	 * 第三方支付单号.
	 */
	public void setPayId(String payId) {
		this.payId = payId;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	/** 
	 * 版本号.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/** 
	 * 付款码.
	 */
	@Column(name = "PAYMENT_CODE", length = 60)
	public String getPaymentCode() {
		return this.paymentCode;
	}

	/** 
	 * 付款码.
	 */
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	/** 
	 * 请求参数.
	 */
	@Column(name = "REQUEST_PARAM", length = 2048)
	public String getRequestParam() {
		return this.requestParam;
	}

	/** 
	 * 请求参数.
	 */
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	/** 
	 * 响应参数.
	 */
	@Column(name = "RESPONSE_PARAMS", length = 2048)
	public String getResponseParams() {
		return this.responseParams;
	}

	/** 
	 * 响应参数.
	 */
	public void setResponseParams(String responseParams) {
		this.responseParams = responseParams;
	}

	/** 
	 * 银行单号.
	 */
	@Column(name = "BANK_ID", length = 60)
	public String getBankId() {
		return this.bankId;
	}

	/** 
	 * 银行单号.
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

}
