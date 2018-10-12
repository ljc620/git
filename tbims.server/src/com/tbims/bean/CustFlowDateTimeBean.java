package com.tbims.bean;


/**
 * 
* Title: 客流时间段统计  <br/>
* Description: 
* @ClassName: CustFlowDateTimeBean
* @author syq
* @date 2017年7月14日 上午10:59:57
*
 */
public class CustFlowDateTimeBean {
	private String rptDate;//统计时间段
	private Long venueId;//场馆编号
	private String venueName;//场馆名称 
	private Long regionId;//区域号
	private String regionName;//区域名称
	private String ticketTypeId;//票种
	private String ticketTypeName;//票种名称
	private Long checkTicketNum;//检票数量
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getRptDate() {
		return rptDate;
	}
	public void setRptDate(String rptDate) {
		this.rptDate = rptDate;
	}
	public Long getVenueId() {
		return venueId;
	}
	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Long getCheckTicketNum() {
		return checkTicketNum;
	}
	public void setCheckTicketNum(Long checkTicketNum) {
		this.checkTicketNum = checkTicketNum;
	}
}
