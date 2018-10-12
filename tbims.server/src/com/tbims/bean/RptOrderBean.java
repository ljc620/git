package com.tbims.bean;

import java.util.List;

public class RptOrderBean {
/**
* 销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
*/
private String orderType;

private String orderTypeDesc;


private List<RptPayDetail> payDetailList;

public String getOrderType() {
	return orderType;
}

public void setOrderType(String orderType) {
	this.orderType = orderType;
}

public String getOrderTypeDesc() {
	return orderTypeDesc;
}

public void setOrderTypeDesc(String orderTypeDesc) {
	this.orderTypeDesc = orderTypeDesc;
}

public List<RptPayDetail> getPayDetailList() {
	return payDetailList;
}

public void setPayDetailList(List<RptPayDetail> payDetailList) {
	this.payDetailList = payDetailList;
}






}
