package com.kdw.wb.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseResBody<T> {
	private final T data;
	private final String message;
	public ResponseEntity<BaseResBody<T>> toResponse(HttpStatus status){
		return ResponseEntity.status(status)
				.body(this);
	}
}
