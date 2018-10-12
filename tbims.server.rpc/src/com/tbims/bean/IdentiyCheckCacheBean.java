package com.tbims.bean;

import java.util.Date;

/**
 * Title: 身份证检票缓存bean <br/>
 * Description:
 * 
 * @ClassName: IdentiyCheckCacheBean
 * @author ydc
 * @date 2017年11月8日 上午9:20:16
 * 
 */
public class IdentiyCheckCacheBean {
	/**
	*检票ID
	*/
	private String checkId;

	/**
	 * 身份证号
	 */
	private String identiy;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单明细号
	 */
	private String orderDetailId;
	/**
	 * 检票时间
	 */
	private Date checkTime;

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getIdentiy() {
		return identiy;
	}

	public void setIdentiy(String identiy) {
		this.identiy = identiy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

}
