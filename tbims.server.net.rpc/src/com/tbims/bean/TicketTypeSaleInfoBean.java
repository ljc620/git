package com.tbims.bean;

/**
 * Title: 按票种汇总售票信息 <br/>
 * Description:
 * @ClassName: TicketTypeSaleInfoBean
 * @author ydc
 * @date 2017年6月27日 下午6:22:21
 * 
 */
public class TicketTypeSaleInfoBean {
	/**
	 * 票种
	 */
	String ticketTypeId;
	/**
	 * 售票数量
	 */
	Long ticketCount;
	/**
	 * 售票金额
	 */
	Long realSumAmt;
	/**
	* 销售单价
	*/
	Long salePrice;

	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Long getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}
	public Long getRealSumAmt() {
		return realSumAmt;
	}
	public void setRealSumAmt(Long realSumAmt) {
		this.realSumAmt = realSumAmt;
	}
	public Long getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

}
