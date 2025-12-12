package com.kdw.wb.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.kdw.wb.common.BaseResBody;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(WhiteboardException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(WhiteboardException ex){
		log.info(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ex.getErrorCode()), new StringBuilder().append(ex.getMessage()).append('\n').append(ex.getExtraMessage()).toString())
				.toResponse(ex.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MethodArgumentNotValidException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ex.getFieldError().getDefaultMessage()), "システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MethodArgumentTypeMismatchException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ex.getPropertyName() + " require " + ex.getRequiredType().getSimpleName()), 
				"システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(HttpMessageNotReadableException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData("invalid request body. check format"),
				"システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MissingServletRequestParameterException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ex.getMessage()), 
				"システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(HttpRequestMethodNotSupportedException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ex.getMessage()),
				"システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(NoResourceFoundException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData("check url"), 
				"システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(MultipartException ex){
		log.warn(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ex.getMessage()), 
				"システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResBody<BaseErrorData>> handleException(Exception ex){
		log.error(ex.getMessage(), ex);
		return new BaseResBody<>(new BaseErrorData(ErrorCode.UNKNOWN_ERROR), "システムエラーが発生しました。管理者にお問い合わせください")
				.toResponse(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
