package com.webservice.entity;

import java.util.Date;

/**
 * Title:订单明细 <br/>
 * Description:
 * 
 * @ClassName: OrderDetail
 * @author ydc
 * @date 2017年7月25日 上午11:38:58
 * 
 */
public class OrderDetail {
	/**
	 * 票种编号
	 */
	private String ticketTypeId;
	/**
	 * 身份证号
	 */
	private String identityCode;
	/**
	 * 销售价
	 */
	private long salePrice;
	/**
	 * 入园限定日期-开始（>=此日期方可入园）
	 */
	private Date startDate;
	/**
	 * 入园限定日期-截止（<=此日期方可入园），如果截止日期不限定，则传入日期最大值
	 */
	private Date endDate;

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
