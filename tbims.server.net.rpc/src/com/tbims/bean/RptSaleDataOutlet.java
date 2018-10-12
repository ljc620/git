package com.tbims.bean;

import java.util.Date;

/**
 * Title: 网点销售统计表 <br/>
 * Description:
 * @ClassName: RptSaleDataBean
 * @author ydc
 * @date 2017年7月13日 下午3:56:02
 * 
 */
public class RptSaleDataOutlet {
	/** 交易日期 */
	private Date rtpDate;
	/** 网点编号 */
	private Long outletId;
	/** 网点名称 */
	private String outletName;
	/** 支付方式编码 */
	private String payName;
	/** 支付方式名称 */
	private String payType;
	/** 票种名称 */
	private String ticketTypeId;
	/** 票种编码 */
	private String ticketTypeName;
	/** 销售数量 */
	private Long saleNum;
	/** 销售总金额 */
	private double saleSumAmt;

	public Date getRtpDate() {
		return rtpDate;
	}
	public void setRtpDate(Date rtpDate) {
		this.rtpDate = rtpDate;
	}
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	public Long getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}
	public double getSaleSumAmt() {
		return saleSumAmt;
	}
	public void setSaleSumAmt(double saleSumAmt) {
		this.saleSumAmt = saleSumAmt;
	}
}
