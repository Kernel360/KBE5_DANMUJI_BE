package com.back2basics.service.company.exception;

import com.back2basics.response.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyErrorCode implements ErrorCode {

    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMP001", "회사를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}