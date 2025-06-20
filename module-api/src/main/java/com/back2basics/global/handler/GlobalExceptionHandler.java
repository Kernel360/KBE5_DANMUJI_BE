package com.back2basics.global.handler;

import static com.back2basics.global.response.code.CommonErrorCode.BAD_CREDENTIALS;
import static com.back2basics.global.response.code.CommonErrorCode.INTERNAL_SERVER_ERROR;
import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DOWNLOAD_FAIL;
import static com.back2basics.infra.exception.user.UserErrorCode.MAIL_SEND_FAILED;
import static com.back2basics.security.code.AuthErrorCode.ACCESS_DENIED;

import com.back2basics.global.response.code.CommonErrorCode;
import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.CustomException;
import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.infra.exception.ForbiddenAccessException;
import com.back2basics.infra.exception.company.DuplicateCompanyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleIOException(IOException ex) {
        ErrorCode errorCode = FILE_DOWNLOAD_FAIL;
        log.error("IOException 발생: {}", ex.getMessage(), ex);
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    // NullPointerException 대응 추가 (customUserDetails == null 일 떄)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNullPointerException(
        NullPointerException ex) {
        String msg = ex.getMessage();
        if (msg != null && msg.contains("CustomUserDetails")) {
            ErrorCode errorCode = ACCESS_DENIED;
            log.warn("인증되지 않은 사용자 요청 (CustomUserDetails is null): {}", msg);
            return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
        }
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        log.error("NullPointerException 발생: {}", ex.getMessage(), ex);
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadCredentials(
        BadCredentialsException ex) {
        ErrorCode errorCode = BAD_CREDENTIALS;
        log.error("BadCredentialsException 발생: {}", ex.getMessage());
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    // 잘못된 json 구조를 body로 전달할 경우
    @ExceptionHandler(com.fasterxml.jackson.databind.JsonMappingException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleJsonMappingException(
        com.fasterxml.jackson.databind.JsonMappingException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("JsonMappingException 발생: {}", ex.getMessage(), ex);
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    // json body에서 타입 잘못 넣었을 경우(id 필드에 문자열값 넣고 이런거)
    @ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleJsonParseException(
        com.fasterxml.jackson.core.JsonParseException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("JsonParseException 발생: {}", ex.getMessage(), ex);
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    //형 변환 실패 시
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException ex) {

        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("HttpMessageNotReadableException 발생: {}", ex.getMessage(), ex);
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    // DB 제약조건 위배하는 경우
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleDataIntegrityViolationException(
        org.springframework.dao.DataIntegrityViolationException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("DataIntegrityViolationException 발생: {}", ex.getMessage(), ex);
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    // 찾는거 없는 경우(Optional.orElseThrow 같은거)
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleNoSuchElementException(
        NoSuchElementException ex) {
        ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
        log.error("NoSuchElementException 발생: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // @RequestParam 등에서 타입이 맞지 않을 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_TYPE_VALUE;
        log.error("MethodArgumentTypeMismatchException 발생:{}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(ex);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // HTTP 메소드 타입 잘못 줬을 때
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException ex) {
        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
        log.error("HttpRequestMethodNotSupportedException 발생: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 요청 파라미터가 누락됐을 때
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException ex) {
        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
        log.error("HttpRequestMethodNotSupportedException 발생: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 폼 데이터 바인딩할 때 유효성 검사 실패하면 여기서 잡아줌
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleBindException(BindException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("BindException 발생: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getBindingResult());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // @Validated 먹여준 @RequestParam이나 @PathVariable 같은 데서 유효성 검사 실패하면 여기서 처리함
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleConstraintViolationException(
        ConstraintViolationException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("ConstraintViolationException 발생: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getConstraintViolations());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // @Valid + @RequestBody, @Validated 에서 binding error 발생 시(@Valid command)
    // json 바디 같은 거 @Valid 붙여서 검사하다가 실패하면 여기서 처리하는 거임
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        ErrorCode errorCode = CommonErrorCode.INVALID_INPUT_VALUE;
        log.error("MethodArgumentNotValidException 발생: {}", errorCode.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getBindingResult());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 메일 전송 실패 시
    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMailException(MailException ex) {
        ErrorCode errorCode = MAIL_SEND_FAILED;
        log.error("MailException 발생: {}", errorCode.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getMessage());
        return ApiResponse.error(errorCode, errorResponse);
    }

    // 비즈니스 요구사항에 따라 커스텀해준 익셉션들
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("CustomException 발생: {}", ex.getMessage(), ex);
        return ex.getErrors().isEmpty()
            ? ApiResponse.error(errorCode, ErrorResponse.of(errorCode))
            : ApiResponse.error(errorCode, ErrorResponse.of(errorCode, ex.getErrors()));
    }

    // 그 밖에 발생하는 모든 예외처리는 여기서 잡음

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex,
        HttpServletRequest request) {
        // SSE 요청이면 ApiResponse 형식으로 응답하지 않음
        if ("text/event-stream".equals(request.getHeader("Accept"))) {
            return ResponseEntity.noContent().build(); // 204 응답으로 무시
        }

        ErrorCode errorCode = INTERNAL_SERVER_ERROR;
        log.error("UnhandledException 발생: {}", ex.getMessage());
        return ApiResponse.error(errorCode, ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(DuplicateCompanyException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleDuplicateCompanyException(
        DuplicateCompanyException ex) {

        log.warn("중복 회사 생성 예외 발생: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getErrors());

        return ApiResponse.error(ex.getErrorCode(), response);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleForbiddenAccessException(
        ForbiddenAccessException ex) {

        log.warn("문의 사항 삭제 예외 발생: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getErrors());

        return ApiResponse.error(ex.getErrorCode(), response);
    }
}
