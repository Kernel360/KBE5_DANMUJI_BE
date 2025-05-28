package com.back2basics.global.handler;

import com.back2basics.global.response.code.CommonErrorCode;
import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.CustomException;
import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 찾는거 없는 경우(Optional.orElseThrow 같은거)
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleNoSuchElementException(
        NoSuchElementException ex) {
        ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
        log.error("NoSuchElementException 발생 {}: {}", ex.getClass().getSimpleName(),
            ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // @RequestParam 등에서 타입이 맞지 않을 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_TYPE_VALUE;
        log.error("MethodArgumentTypeMismatchException 발생 {}: {}", ex.getClass().getSimpleName(),
            ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(ex);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // HTTP 메소드 타입 잘못 줬을 때
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException ex) {
        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
        log.error("HttpRequestMethodNotSupportedException 발생 {}: {}", ex.getClass().getSimpleName(),
            ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 요청 파라미터가 누락됐을 때
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException ex) {
        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
        log.error("HttpRequestMethodNotSupportedException 발생 {}: {}", ex.getClass().getSimpleName(),
            ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 폼 데이터 바인딩할 때 유효성 검사 실패하면 여기서 잡아줌
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleBindException(BindException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("BindException 발생 {}: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getBindingResult());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // @Validated 먹여준 @RequestParam이나 @PathVariable 같은 데서 유효성 검사 실패하면 여기서 처리함
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleConstraintViolationException(
        ConstraintViolationException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("ConstraintViolationException 발생 {}: {}", ex.getClass().getSimpleName(),
            ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getConstraintViolations());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // @Valid + @RequestBody, @Validated 에서 binding error 발생 시(@Valid command)
    // json 바디 같은 거 @Valid 붙여서 검사하다가 실패하면 여기서 처리하는 거임
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("MethodArgumentNotValidException 발생: {}", errorCode.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getBindingResult());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 비즈니스 요구사항에 따라 커스텀해준 익셉션들
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("CustomException 발생 {}: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return ex.getErrors().isEmpty()
            ? ApiResponse.error(errorCode, ErrorResponse.of(errorCode))
            : ApiResponse.error(errorCode, ErrorResponse.of(errorCode, ex.getErrors()));
    }

    // 그 밖에 발생하는 모든 예외처리는 여기서 잡음
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception ex) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        log.error("UnhandledException 발생 {}: {}", ex.getClass().getSimpleName(), ex.getMessage(),
            ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }
}
