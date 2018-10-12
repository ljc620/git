package com.zhming.support.bean;

import com.zhming.support.util.MsgUtil;

/**
* Title: 返回错误实体  <br/>
* Description: 
* @ClassName: ErrorBean
* @author ydc
* @date 2017年8月4日 下午3:17:03
* 
*/
public class ErrorBean {
	/**
	*错误码
	*/
	int errorCode;
	/**
	*错误码对应的错误信息
	*/
	String errorMsg = "";
	/**
	*自定义抛出异常指定的错误信息
	*/
	String errorDescribe = "";

	public ErrorBean() {
	}

	public ErrorBean(int errorCode) {
		this.errorCode = errorCode;
		this.errorMsg = MsgUtil.getMsg(errorCode);
	}

	public ErrorBean(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorMsg = MsgUtil.getMsg(errorCode);
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorDescribe() {
		return errorDescribe;
	}
	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}

}
