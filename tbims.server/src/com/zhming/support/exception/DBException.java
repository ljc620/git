package com.zhming.support.exception;

/**
* Title: 数据库层处理错误  <br/>
* Description: 
* @ClassName: DBExceptionBframe
* @author ydc
* @date 2016-1-7 下午5:10:32
* 
*/
public class DBException extends BaseException {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	public DBException() {
		super();
	}

	public DBException(Throwable throwable) {
		super(throwable);
	}

	public DBException(int errcode) {
		super(errcode);
	}

	public DBException(int errcode, String throwable) {
		super(errcode, throwable);
	}
	public DBException(int errcode, String msg, Throwable throwable) {
		super(errcode, msg, throwable);
	}
	public DBException(int errcode, Throwable throwable) {
		super(throwable);
	}
}
