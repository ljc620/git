package com.tbims.bean;

import java.util.Date;

/**
 * Title: 网点库存统计表 <br/>
 * Description:
 * @ClassName: RptStrData
 * @author ydc
 * @date 2017年7月18日 下午4:26:18
 * 
 */
public class RptStrData {
	private Date rtpDate;
	private Long outletId;
	private String outletName;
	private String ticketTypeId;
	private String ticketTypeName;
	private Long strLastTicketNum;
	private Long deliveryNum;
	private Long strTicketNum;
	private Long uselessNum;
	private Long saleTicketNum;

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
	public Long getStrLastTicketNum() {
		return strLastTicketNum;
	}
	public void setStrLastTicketNum(Long strLastTicketNum) {
		this.strLastTicketNum = strLastTicketNum;
	}
	public Long getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(Long deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public Long getStrTicketNum() {
		return strTicketNum;
	}
	public void setStrTicketNum(Long strTicketNum) {
		this.strTicketNum = strTicketNum;
	}
	public Long getUselessNum() {
		return uselessNum;
	}
	public void setUselessNum(Long uselessNum) {
		this.uselessNum = uselessNum;
	}
	public Long getSaleTicketNum() {
		return saleTicketNum;
	}
	public void setSaleTicketNum(Long saleTicketNum) {
		this.saleTicketNum = saleTicketNum;
	}
}
