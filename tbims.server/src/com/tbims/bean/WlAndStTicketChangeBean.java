package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * 
 * Title: 网络代理商换票统计报表 <br/>
 * Description:
 * 
 * @ClassName: WlAndStTicketChangeBean
 * @author syq
 * @date 2017年7月11日 上午9:38:56
 *
 */
public class WlAndStTicketChangeBean {

	private String orgId;// 机构号
	private String orgName;// 组织机构名称
	private String ticketTypeId;// 票种编号
	private String ticketTypeName;// 票种名称
	private Long ticketNum;// 销售数量
	private Long totalPrice;// 销售原价
	private Date saleStartTime;// 销售日期
	private Date saleEndTime;// 销售日期
	private Date opeTime;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

}
