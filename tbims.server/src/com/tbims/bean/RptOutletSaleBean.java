package com.tbims.bean;

import java.util.ArrayList;
import java.util.List;

public class RptOutletSaleBean {
	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 网点名称
	 */
	private String outletName;
	
	/**
	 * 小计数量
	 */
	private Long outletSum;
	
	/**
	 * 小计金额
	 */
	private Long outletAmt;
	
	/**
	 * 支付方式列表
	 */
	private List<RptOrderBean> orderList= new ArrayList<RptOrderBean>();

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

	public Long getOutletSum() {
		return outletSum;
	}

	public void setOutletSum(Long outletSum) {
		this.outletSum = outletSum;
	}

	public Long getOutletAmt() {
		return outletAmt;
	}

	public void setOutletAmt(Long outletAmt) {
		this.outletAmt = outletAmt;
	}

	public List<RptOrderBean> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<RptOrderBean> orderList) {
		this.orderList = orderList;
	}
	
	
}
