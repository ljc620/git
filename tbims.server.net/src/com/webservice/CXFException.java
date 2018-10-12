package com.webservice;

import com.zhming.support.util.MsgUtil;

/**
* Title: 自定义webservice 异常 <br/>
* Description: 
* @ClassName: CXFException
* @author ydc
* @date 2017年7月26日 上午10:53:27
* 
*/
public class CXFException extends Exception {
	private static final long serialVersionUID = 1L;

	private int errcode = -1;
	private String errinfo = "";// 是否有详细的错误信息

	public CXFException() {
		super();
	}

	public CXFException(int errcode) {
		super(MsgUtil.getMsg(errcode));
		this.errcode = errcode;
		this.errinfo = MsgUtil.getMsg(errcode);
	}

	public CXFException(int errcode, String msg) {
		super(msg);
		this.errcode = errcode;
		this.errinfo = msg;
	}

	public CXFException(int errcode, Throwable throwable) {
		super(MsgUtil.getMsg(errcode), throwable);
		this.errcode = errcode;
		this.errinfo = MsgUtil.getMsg(errcode);
	}

	public CXFException(int errcode, String msg, Throwable throwable) {
		super(msg, throwable);
		this.errcode = errcode;
		this.errinfo = msg;
	}

	public CXFException(Throwable throwable) {
		super(throwable);
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
