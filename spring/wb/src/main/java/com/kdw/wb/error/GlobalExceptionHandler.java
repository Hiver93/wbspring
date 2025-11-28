package com.kdw.wb.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.kdw.wb.common.BaseResBody;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(WhiteboardException.class)
	public ResponseEntity<BaseResBody<Void>> handleException(WhiteboardException ex){
		return new BaseResBody<Void>(null, ex.getMessage())
				.toResponse(ex.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResBody<Void>> handleException(MethodArgumentNotValidException ex){
		return new BaseResBody<Void>(null, ex.getFieldError().getDefaultMessage())
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<BaseResBody<Void>> handleException(MethodArgumentTypeMismatchException ex){
		return new BaseResBody<Void>(null, ex.getPropertyName() + " require " + ex.getRequiredType().getSimpleName())
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BaseResBody<Void>> handleException(HttpMessageNotReadableException ex){
		return new BaseResBody<Void>(null, "invalid request body. check format")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<BaseResBody<Void>> handleException(MissingServletRequestParameterException ex){
		return new BaseResBody<Void>(null, ex.getMessage())
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<BaseResBody<Void>> handleException(NoResourceFoundException ex){
		return new BaseResBody<Void>(null, "check url")
				.toResponse(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResBody<Void>> handleException(Exception ex){
		log.error(ex.getMessage(), ex);
		return new BaseResBody<Void>(null, "unknown error. contact admin.")
				.toResponse(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
