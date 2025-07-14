package com.back2basics.domain.company.exceptionhandler;

import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.infra.exception.company.DuplicateCompanyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.back2basics.domain.company")
public class CompanyExceptionHandler {

    @ExceptionHandler(DuplicateCompanyException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleDuplicateCompanyException(
        DuplicateCompanyException ex) {

        log.warn("중복 회사 생성 예외 발생: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getErrors());
        return ApiResponse.error(ex.getErrorCode(), response);
    }
}
