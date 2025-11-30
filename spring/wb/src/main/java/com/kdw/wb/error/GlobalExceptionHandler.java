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
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(WhiteboardException ex){
		return new BaseResBody<>(new BaseErrorData(ex.getErrorCode()), ex.getMessage())
				.toResponse(ex.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MethodArgumentNotValidException ex){
		return new BaseResBody<>(new BaseErrorData("システムエラーが発生しました。管理者にお問い合わせください",ex.getFieldError().getDefaultMessage()), ex.getFieldError().getDefaultMessage())
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MethodArgumentTypeMismatchException ex){
		return new BaseResBody<>(new BaseErrorData("システムエラーが発生しました。管理者にお問い合わせください",ex.getPropertyName() + " require " + ex.getRequiredType().getSimpleName()), 
				ex.getPropertyName() + " require " + ex.getRequiredType().getSimpleName())
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(HttpMessageNotReadableException ex){
		return new BaseResBody<>(new BaseErrorData("システムエラーが発生しました。管理者にお問い合わせください","invalid request body. check format"),
				"invalid request body. check format")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MissingServletRequestParameterException ex){
		return new BaseResBody<>(new BaseErrorData("システムエラーが発生しました。管理者にお問い合わせください",ex.getMessage()), 
				ex.getMessage())
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(NoResourceFoundException ex){
		return new BaseResBody<>(new BaseErrorData("システムエラーが発生しました。管理者にお問い合わせください","check url"), 
				"check url")
				.toResponse(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(Exception ex){
		log.error(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ErrorCode.UNKNOWN_ERROR), "unknown error. contact admin.")
				.toResponse(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
