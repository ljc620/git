package com.tbims.entity;

// Generated 2017-8-22 9:08:41 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 对账单明细
 */
@Entity
@Table(name = "SL_BILL_DETAIL", schema = "TBIMS")
public class SlBillDetail implements java.io.Serializable {

	/**
	 * BILL_DETAIL_ID
	 */
	private String billDetailId;

	/**
	 * BILL_ID
	 */
	private String billId;

	/**
	 * 交易时间
	 */
	private String tranTime;

	/**
	 * 商户号
	 */
	private String mchId;

	/**
	 * 平台订单号
	 */
	private String transactionId;

	/**
	 * 第三方订单号
	 */
	private String outTransactionId;

	/**
	 * 商户订单号
	 */
	private String outTradeNo;

	/**
	 * 交易类型
	 */
	private String tranType;

	/**
	 * 交易状态
	 */
	private String tranStatus;

	/**
	 * 总金额
	 */
	private double orderFee;

	/**
	 * 平台退款单号
	 */
	private String refundId;

	/**
	 * 商户退款单号
	 */
	private String outRefundNo;

	/**
	 * 退款金额
	 */
	private double refundFee;

	/**
	 * 退款类型
	 */
	private String refundType;

	/**
	 * 退款状态
	 */
	private String refundStatus;

	/**
	 * 手续费
	 */
	private double serviceFee;

	/**
	 * 实收金额
	 */
	private double collectFee;

	/**
	 * 响应信息
	 */
	private String responseInfo;

	/**
	 * 对账状态 0-对账成功 1-对账失败 N-未对账
	 */
	private String billResult;

	/**
	 * 商品名称 原样返回提交支付时的body参数的值
	 */
	private String bodyStr;

	/**
	 * 商户数据包 原样返回提交支付时的attach参数的值
	 */
	private String attach;

	/**
	 * 备注
	 */
	private String remark;

	public SlBillDetail() {
	}

	public SlBillDetail(String billDetailId) {
		this.billDetailId = billDetailId;
	}

	public SlBillDetail(String billDetailId, String billId, String tranTime, String mchId, String transactionId, String outTransactionId, String outTradeNo, String tranType, String tranStatus, double orderFee, String refundId, String outRefundNo, double refundFee, String refundType, String refundStatus, double serviceFee, double collectFee, String responseInfo, String billResult, String bodyStr,
			String attach, String remak) {
		this.billDetailId = billDetailId;
		this.billId = billId;
		this.tranTime = tranTime;
		this.mchId = mchId;
		this.transactionId = transactionId;
		this.outTransactionId = outTransactionId;
		this.outTradeNo = outTradeNo;
		this.tranType = tranType;
		this.tranStatus = tranStatus;
		this.orderFee = orderFee;
		this.refundId = refundId;
		this.outRefundNo = outRefundNo;
		this.refundFee = refundFee;
		this.refundType = refundType;
		this.refundStatus = refundStatus;
		this.serviceFee = serviceFee;
		this.collectFee = collectFee;
		this.responseInfo = responseInfo;
		this.billResult = billResult;
		this.bodyStr = bodyStr;
		this.attach = attach;
		this.remark = remak;
	}

	/**
	 * BILL_DETAIL_ID.
	 */
	@Id
	@Column(name = "BILL_DETAIL_ID", unique = true, nullable = false, length = 60)
	public String getBillDetailId() {
		return this.billDetailId;
	}

	/**
	 * BILL_DETAIL_ID.
	 */
	public void setBillDetailId(String billDetailId) {
		this.billDetailId = billDetailId;
	}

	/**
	 * BILL_ID.
	 */
	@Column(name = "BILL_ID", length = 60)
	public String getBillId() {
		return this.billId;
	}

	/**
	 * BILL_ID.
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/**
	 * 交易时间.
	 */
	@Column(name = "TRAN_TIME", length = 100)
	public String getTranTime() {
		return this.tranTime;
	}

	/**
	 * 交易时间.
	 */
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	/**
	 * 商户号.
	 */
	@Column(name = "MCH_ID", length = 100)
	public String getMchId() {
		return this.mchId;
	}

	/**
	 * 商户号.
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	/**
	 * 平台订单号.
	 */
	@Column(name = "TRANSACTION_ID", length = 100)
	public String getTransactionId() {
		return this.transactionId;
	}

	/**
	 * 平台订单号.
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * 第三方订单号.
	 */
	@Column(name = "OUT_TRANSACTION_ID", length = 100)
	public String getOutTransactionId() {
		return this.outTransactionId;
	}

	/**
	 * 第三方订单号.
	 */
	public void setOutTransactionId(String outTransactionId) {
		this.outTransactionId = outTransactionId;
	}

	/**
	 * 商户订单号.
	 */
	@Column(name = "OUT_TRADE_NO", length = 100)
	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	/**
	 * 商户订单号.
	 */
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	/**
	 * 交易类型.
	 */
	@Column(name = "TRAN_TYPE", length = 100)
	public String getTranType() {
		return this.tranType;
	}

	/**
	 * 交易类型.
	 */
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	/**
	 * 交易状态.
	 */
	@Column(name = "TRAN_STATUS", length = 100)
	public String getTranStatus() {
		return this.tranStatus;
	}

	/**
	 * 交易状态.
	 */
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	/**
	 * 总金额.
	 */
	@Column(name = "ORDER_FEE", precision = 12, scale = 2)
	public double getOrderFee() {
		return this.orderFee;
	}

	/**
	 * 总金额.
	 */
	public void setOrderFee(double orderFee) {
		this.orderFee = orderFee;
	}

	/**
	 * 平台退款单号.
	 */
	@Column(name = "REFUND_ID", length = 100)
	public String getRefundId() {
		return this.refundId;
	}

	/**
	 * 平台退款单号.
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	/**
	 * 商户退款单号.
	 */
	@Column(name = "OUT_REFUND_NO", length = 100)
	public String getOutRefundNo() {
		return this.outRefundNo;
	}

	/**
	 * 商户退款单号.
	 */
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	/**
	 * 退款金额.
	 */
	@Column(name = "REFUND_FEE", precision = 12, scale = 2)
	public double getRefundFee() {
		return this.refundFee;
	}

	/**
	 * 退款金额.
	 */
	public void setRefundFee(double refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * 退款类型.
	 */
	@Column(name = "REFUND_TYPE", length = 100)
	public String getRefundType() {
		return this.refundType;
	}

	/**
	 * 退款类型.
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	/**
	 * 退款状态.
	 */
	@Column(name = "REFUND_STATUS", length = 100)
	public String getRefundStatus() {
		return this.refundStatus;
	}

	/**
	 * 退款状态.
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	/**
	 * 手续费.
	 */
	@Column(name = "SERVICE_FEE", precision = 12, scale = 2)
	public double getServiceFee() {
		return this.serviceFee;
	}

	/**
	 * 手续费.
	 */
	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * 实收金额.
	 */
	@Column(name = "COLLECT_FEE", precision = 12, scale = 2)
	public double getCollectFee() {
		return this.collectFee;
	}

	/**
	 * 实收金额.
	 */
	public void setCollectFee(double collectFee) {
		this.collectFee = collectFee;
	}

	/**
	 * 响应信息.
	 */
	@Column(name = "RESPONSE_INFO", length = 2048)
	public String getResponseInfo() {
		return this.responseInfo;
	}

	/**
	 * 响应信息.
	 */
	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	/**
	 * 对账状态 0-对账成功 1-对账失败 N-未对账
	 */
	@Column(name = "BILL_RESULT", length = 1)
	public String getBillResult() {
		return this.billResult;
	}

	/**
	 * 对账状态 0-对账成功 1-对账失败 N-未对账
	 */
	public void setBillResult(String billResult) {
		this.billResult = billResult;
	}

	/**
	 * 商品名称 原样返回提交支付时的body参数的值.
	 */
	@Column(name = "BODY_STR", length = 1024)
	public String getBodyStr() {
		return this.bodyStr;
	}

	/**
	 * 商品名称 原样返回提交支付时的body参数的值.
	 */
	public void setBodyStr(String bodyStr) {
		this.bodyStr = bodyStr;
	}

	/**
	 * 商户数据包 原样返回提交支付时的attach参数的值.
	 */
	@Column(name = "ATTACH", length = 1024)
	public String getAttach() {
		return this.attach;
	}

	/**
	 * 商户数据包 原样返回提交支付时的attach参数的值.
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
