package com.zhming.support.bean;

import java.util.List;

public class EchartsBean {

	private List<String> xValue;
	private List<String> yValue;
	private List<String> zValue;
	public List<String> getxValue() {
		return xValue;
	}
	public void setxValue(List<String> xValue) {
		this.xValue = xValue;
	}
	public List<String> getyValue() {
		return yValue;
	}
	public void setyValue(List<String> yValue) {
		this.yValue = yValue;
	}
	public EchartsBean(List<String> xValue, List<String> yValue) {
		super();
		this.xValue = xValue;
		this.yValue = yValue;
	}
	public EchartsBean() {
		super();
	}
	public List<String> getzValue() {
		return zValue;
	}
	public void setzValue(List<String> zValue) {
		this.zValue = zValue;
	}
	public EchartsBean(List<String> xValue, List<String> yValue, List<String> zValue) {
		super();
		this.xValue = xValue;
		this.yValue = yValue;
		this.zValue = zValue;
	}

}
