package com.tbims.face.service;


public class EpeatVerificationException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public EpeatVerificationException() {
		
	}

	public EpeatVerificationException(String message) {
		super(message);
		
	}

	public EpeatVerificationException(Throwable cause) {
		super(cause);
		
	}

	public EpeatVerificationException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public EpeatVerificationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
