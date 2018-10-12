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
public class WlOrgChangeBean {
	/**
	 * 机构号
	 */
	private String orgId;
	/**
	 * 组织机构名称
	 */
	private String orgName;
	/**
	 * 网点号
	 */
	private Long outletId;
	/**
	 * 网点名称
	 */
	private String outletName;
	/**
	 * 票种编号
	 */
	private String ticketTypeId;
	/**
	 * 票种名称
	 */
	private String ticketTypeName;
	/**
	 * 支付方式
	 */
	private String payType;
	/**
	 * 支付名称
	 */
	private String itemName;
	/**
	 * 销售数量
	 */
	private Long saleNum;
	/**
	 * 销售金额
	 */
	private Long saleAmt;
	/**
	 * 出票数量
	 */
	private Long ejectNum;
	/**
	 * 退票数量
	 */
	private Long tradeNum;
	/**
	 * 退票金额
	 */
	private Long tradeAmt;
	/**
	 * 售票日期（开始日期）
	 */
	private Date opeTime;
	/**
	 * 售票日期（结束日期）
	 */
	private Date opeTime2;
	
	/**
	*门票类型
	*/
	private String ticketClass;
	
	/**
	*检票数量
	*/
	private Long checkNum;
	
	/**
	*检票金额
	*/
	private Long checkAmt;

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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Long getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(Long saleAmt) {
		this.saleAmt = saleAmt;
	}

	public Long getEjectNum() {
		return ejectNum;
	}

	public void setEjectNum(Long ejectNum) {
		this.ejectNum = ejectNum;
	}

	public Long getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(Long tradeNum) {
		this.tradeNum = tradeNum;
	}

	public Long getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Long tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	@JsonSerialize(using=DateSerializer.class)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
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
	
	@JsonSerialize(using=DateSerializer.class)
	public Date getOpeTime2() {
		return opeTime2;
	}

	public void setOpeTime2(Date opeTime2) {
		this.opeTime2 = opeTime2;
	}

	public String getTicketClass() {
		return ticketClass;
	}

	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	public Long getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(Long checkNum) {
		this.checkNum = checkNum;
	}

	public Long getCheckAmt() {
		return checkAmt;
	}

	public void setCheckAmt(Long checkAmt) {
		this.checkAmt = checkAmt;
	}
}
