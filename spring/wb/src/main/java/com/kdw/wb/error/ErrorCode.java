package com.kdw.wb.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

	CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "content not found"),
	
	AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "login please"),
	
	INCORRECT_PASSKEY(HttpStatus.FORBIDDEN, "incorrect passkey"),
	INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "incorrect password"),
	
	INVALID_CONTENT_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "unsupported content type"),
	
	PASSKEY_REQUIRED(HttpStatus.BAD_REQUEST, "passkey is required")
	;
	private final HttpStatus status;
	private final String message;
}