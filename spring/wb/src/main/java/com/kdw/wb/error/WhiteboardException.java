package com.kdw.wb.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class WhiteboardException extends RuntimeException {
	private final ErrorCode errorCode;
	private String extraMessage = "";

	public WhiteboardException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public WhiteboardException(ErrorCode errorCode, String extraMessage) {
		super();
		this.errorCode = errorCode;
		this.extraMessage = extraMessage;
	}
	
	public HttpStatus getStatus() {
		return this.errorCode.getStatus();
	}
	
	public String getMassage() {
		return this.errorCode.getMessage();
	}
}
