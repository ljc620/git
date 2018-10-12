package com.tbims.bean;


import java.util.Date;

public class SyntheticalBean {
	/** 
	 * 统计日期
	 */
	private Date rtpDate;
	/** 
	 * 开始日期
	 */
	private Date startDate;
	/** 
	 * 结束日期
	 */
	private Date endDate;
	/** 
	 * 票种编号
	 */
	private String ticketTypeId;
	/** 
	 * 票种名称
	 */
	private String ticketTypeName;
	/** 
	 * 入园张数
	 */
	private Long checkNum;

	/** 
	 * 售票张数
	 */
	private Long saleNum;
	/** 
	 * 库存张数
	 */
	private Long ticketNum;
	
	public Date getRtpDate() {
		return rtpDate;
	}
	public void setRtpDate(Date rtpDate) {
		this.rtpDate = rtpDate;
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
	public Long getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(Long checkNum) {
		this.checkNum = checkNum;
	}
	
	public Long getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}
	public Long getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}
	

}
