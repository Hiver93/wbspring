package com.kdw.wb.error;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseErrorData {

	private final String detail;
	
	@Builder
	public BaseErrorData(String detail) {
		this.detail = detail;
	}
	
	public BaseErrorData(ErrorCode errorCode) {
		this.detail = errorCode.getDetail();
	}
}
