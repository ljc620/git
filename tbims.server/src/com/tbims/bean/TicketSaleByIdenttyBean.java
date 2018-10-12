package com.tbims.bean;

/**
 * Title: 身份证售票记录列表 <br/>
 * Description:
 * 
 * @ClassName: TicketSaleByIdenttyBean
 * @author ydc
 * @date 2017年9月15日 上午10:02:48
 * 
 */
public class TicketSaleByIdenttyBean {
	/** 销售明细表ID */
	private String orderDetailId;
	/** 身份证号 */
	private String identtyId;
	/** 票种编号 */
	private String ticketTypeId;
	/** 票种名称 */
	private String ticketTypeName;
	/** 售票时间 */
	private String saleTime;
	/** 是否检票(Y是N否) */
	private String checkFlag;
	/**
	 * 检票时间
	 */
	private String checkTicketTime;
	/** 支付方式 */
	private String payType;
	/** 有效开始日期 */
	private String validStartDate;
	/** 有效结束日期 */
	private String validEndDate;
	/** 取票状态(1-未取票 2-已取票) */
	private String ejectTicketStat;
	/** 取票时间 */
	private String ejectTicketTime;
	
	/**
	 * 是否退款
	 */
	private String uselessFlag;
	
	/**
	 * 退款时间
	 */
	private String uselessTime;
	/** 销售单号 */
	private String orderId;

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getIdenttyId() {
		return identtyId;
	}

	public void setIdenttyId(String identtyId) {
		this.identtyId = identtyId;
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

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(String validStartDate) {
		this.validStartDate = validStartDate;
	}

	public String getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getEjectTicketStat() {
		return ejectTicketStat;
	}

	public void setEjectTicketStat(String ejectTicketStat) {
		this.ejectTicketStat = ejectTicketStat;
	}

	public String getEjectTicketTime() {
		return ejectTicketTime;
	}

	public void setEjectTicketTime(String ejectTicketTime) {
		this.ejectTicketTime = ejectTicketTime;
	}

	public String getCheckTicketTime() {
		return checkTicketTime;
	}

	public void setCheckTicketTime(String checkTicketTime) {
		this.checkTicketTime = checkTicketTime;
	}

	public String getUselessFlag() {
		return uselessFlag;
	}

	public String getUselessTime() {
		return uselessTime;
	}

	public void setUselessFlag(String uselessFlag) {
		this.uselessFlag = uselessFlag;
	}

	public void setUselessTime(String uselessTime) {
		this.uselessTime = uselessTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
