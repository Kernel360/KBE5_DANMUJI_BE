package com.back2basics.infra.exception.approval;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApprovalErrorCode implements ErrorCode {

    APPROVAL_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "승인 요청을 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}