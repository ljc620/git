package com.zhming.support.exception;

/**
* Title:  业务逻辑层处理异常 <br/>
* Description: 
* @ClassName: BFExceptionBframe
* @author ydc
* @date 2016-1-7 下午5:10:19
* 
*/
public class ServiceException extends BaseException {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}
	/**
	* <p>Title: </p>
	* <p>Description: 错误代码对应的错误信息回显前台</p>
	* @param errcode 错误代码
	*/
	
	public ServiceException(int errcode) {
		super(errcode);
	}

	/**
	* <p>Title: </p>
	* <p>Description: 如果错误代码+错误信息详细回显前台</p>
	* @param errcode 错误代码
	* @param msg  详细的错误信息
	*/
	
	public ServiceException(int errcode, String msg) {
		super(errcode, msg);
	}
	public ServiceException(int errcode, Throwable throwable) {
		super(errcode, throwable);
	}
	public ServiceException(int errcode, String msg, Throwable throwable) {
		super(errcode, msg, throwable);
	}
}
