package com.tbims.pay.bean;

import java.util.Map;

/**
 * Title: 请求返回信息 <br/>
 * Description:
 * 
 * @ClassName: SendDataBean
 * @author ydc
 * @date 2017年8月11日 下午4:27:18
 * 
 */
public class SendDataBean {

	/**
	 * 请求参数map
	 */
	private Map<String, String> requestParamsMap;

	/**
	 * 请求参数xml
	 */
	private String requestParamsXML;

	/**
	 * 响应参数map
	 */
	private Map<String, String> responseParamsMap;

	/**
	 * 响应参数xml
	 */
	private String responseParamsXML;

	public Map<String, String> getRequestParamsMap() {
		return requestParamsMap;
	}

	public void setRequestParamsMap(Map<String, String> requestParamsMap) {
		this.requestParamsMap = requestParamsMap;
	}

	public String getRequestParamsXML() {
		return requestParamsXML;
	}

	public void setRequestParamsXML(String requestParamsXML) {
		this.requestParamsXML = requestParamsXML;
	}

	public Map<String, String> getResponseParamsMap() {
		return responseParamsMap;
	}

	public void setResponseParamsMap(Map<String, String> responseParamsMap) {
		this.responseParamsMap = responseParamsMap;
	}

	public String getResponseParamsXML() {
		return responseParamsXML;
	}

	public void setResponseParamsXML(String responseParamsXML) {
		this.responseParamsXML = responseParamsXML;
	}
}
