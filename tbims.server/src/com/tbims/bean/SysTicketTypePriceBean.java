package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * 
* Title: 阶梯票价表（阶梯票价历史表）bean<br/>
* Description: 
* @ClassName: SysTicketTypePriceBean
* @author syq
* @date 2017年9月6日 下午2:08:10
*
 */
public class SysTicketTypePriceBean {
	/** 
	 * 主键
	 */
	private String priceId;

	/** 
	 * 票种代码
	 */
	private String ticketTypeId;

	/** 
	 * 开始张数
	 */
	private Long startNo;

	/** 
	 * 结束张数
	 */
	private Long endNo;

	/** 
	 * 票价
	 */
	private Long price;

	/** 
	 * 创建时间
	 */
	private Date createTime;

	/** 
	 * 操作人号
	 */
	private String createUserId;
	/** 
	 * 操作人名称
	 */
	private String createUserName;
	
	/** 
	 * 更新时间
	 */
	private Date endTime;
	
	/** 
	 * 最后更新人号
	 */
	private String updateUserId;
	/** 
	 * 最后更新人名称
	 */
	private String updateUserName;
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Long getStartNo() {
		return startNo;
	}
	public void setStartNo(Long startNo) {
		this.startNo = startNo;
	}
	public Long getEndNo() {
		return endNo;
	}
	public void setEndNo(Long endNo) {
		this.endNo = endNo;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
}
