package com.zhming.support.exception;

import com.zhming.support.util.MsgUtil;

/**
 * Title: 自定义异常 <br/>
 * Description:
 * @ClassName: BFException
 * @author ydc
 * @date 2016-1-7 下午5:10:03
 * 
 */
public class BaseException extends Exception {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	private int errcode = -1;
	private String errinfo = "";// 是否有详细的错误信息

	public BaseException() {
		super();
	}

	public BaseException(int errcode) {
		super(MsgUtil.getMsg(errcode));
		this.errcode = errcode;
		this.errinfo = MsgUtil.getMsg(errcode);
	}

	public BaseException(int errcode, String msg) {
		super(msg);
		this.errcode = errcode;
		this.errinfo = msg;
	}

	public BaseException(int errcode, Throwable throwable) {
		super(MsgUtil.getMsg(errcode), throwable);
		this.errcode = errcode;
		this.errinfo = MsgUtil.getMsg(errcode);
	}

	public BaseException(int errcode, String msg, Throwable throwable) {
		super(msg, throwable);
		this.errcode = errcode;
		this.errinfo = msg;
	}

	public BaseException(Throwable throwable) {
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
