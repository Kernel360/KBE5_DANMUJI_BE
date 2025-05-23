package com.back2basics.global.handler;

import com.back2basics.response.global.code.CommonErrorCode;
import com.back2basics.response.global.code.ErrorCode;
import com.back2basics.response.global.error.CustomException;
import com.back2basics.response.global.result.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex) {
    log.warn("CustomException 발생: {}", ex.getErrorCode().getMessage());
    ErrorCode errorCode = ex.getErrorCode();
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode).getBody());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
    log.error("서버 에러 발생", ex);
    return ResponseEntity
        .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR).getBody());
  }
}
