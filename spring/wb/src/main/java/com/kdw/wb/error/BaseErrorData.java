package com.kdw.wb.error;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseErrorData {

	private final String message;
	private final String detail;
	
	@Builder
	public BaseErrorData(String message, String detail) {
		this.message = message;
		this.detail = detail;
	}
	
	public BaseErrorData(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.detail = errorCode.getDetail();
	}
}
