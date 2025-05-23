package com.back2basics.global.handler;

import com.back2basics.response.global.code.CommonErrorCode;
import com.back2basics.response.global.code.ErrorCode;
import com.back2basics.response.global.error.CustomException;
import com.back2basics.response.global.error.ErrorResponse.FieldError;
import com.back2basics.response.global.result.ApiResponse;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(
		NoSuchElementException ex) {
		ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
		log.error("NoSuchElementException 발생: {}", errorCode.getMessage());
		return ApiResponse.error(errorCode);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
		log.error("MethodArgumentTypeMismatchException 발생: {}", errorCode.getMessage());
		return ApiResponse.error(errorCode);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
		log.error("HttpRequestMethodNotSupportedException 발생: {}", errorCode.getMessage());
		return ApiResponse.error(errorCode);
	}

	// @Valid, @Validated 에서 binding error 발생 시 (@RequestBody)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse<List<FieldError>>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
		List<FieldError> fieldErrors = FieldError.of(e.getBindingResult());
		log.error("MethodArgumentNotValidException 발생: {}", errorCode.getMessage());
		return ApiResponse.error(errorCode, fieldErrors);
	}

	// 비즈니스 요구사항에 따라 커스텀해준 익셉션들
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.error("CustomException 발생: {}", e.getErrorCode().getMessage());
		return ApiResponse.error(errorCode);
	}

	// 그 밖에 발생하는 모든 예외처리는 여기서 잡음
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		log.error("서버 에러 발생 {}", e.getMessage());
		ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
		return ApiResponse.error(errorCode);
	}
}
