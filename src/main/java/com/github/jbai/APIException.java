package com.github.jbai;

public class APIException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 821114710348167438L;
	
	private int errorCode;

	public APIException(int code, String message) {
		super(message);
		this.errorCode = code;
	}
	
	public APIException(int code, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = code;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int code) {
		this.errorCode = code;
	}
	
	
}
