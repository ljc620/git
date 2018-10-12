package com.tbims.face.common;

import java.io.Serializable;

/**
 * 响应实体类
 * @author develop3
 *
 * @param <T>
 */
public class JsonResult<T>  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS=0;
	public static final int ERROR=1;
	
	//状态码
	private int state;
	
	//返回数据
	private T data;
	
	//返回消息
	private String message;
	
	public JsonResult() {
		
	}
	public JsonResult(int state) {
		this.state = state;
		message = "";
		data=null;
	}
	public JsonResult(int state,T t) {
		this.state = state;
		message = "";
		data=t;
	}
	
	public JsonResult(int state,Throwable e ) {
		this.state = state;
		message = e.getMessage();
		data=null;
	}
	public JsonResult(T t) {
		state = SUCCESS;
		data = t;
		message = "";
	}
	public JsonResult(Throwable e) {
		state = ERROR;
		data = null;
		message = e.getMessage();
	}
	public JsonResult(int i, String message) {
		state = i;
		data = null;
		this.message = message;
	}
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static int getSuccess() {
		return SUCCESS;
	}
	public static int getError() {
		return ERROR;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + state;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonResult other = (JsonResult) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (state != other.state)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", data=" + data + ", message=" + message + "]";
	}
	
	
	
	
}
