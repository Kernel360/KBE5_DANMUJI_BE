package com.back2basics.infra.exception.company;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyErrorCode implements ErrorCode {

    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMP001", "회사를 찾을 수 없습니다."),
    DUPLICATE_COMPANY_INFO(HttpStatus.CONFLICT, "COMP002", "이미 존재하는 회사 정보입니다."),
    COMPANY_ALREADY_RESTORED(HttpStatus.BAD_REQUEST, "COMP003", "이미 복구된 회사입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}