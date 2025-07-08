package com.back2basics.infra.exception.assignment;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AssignmentErrorCode implements ErrorCode {
    NOT_ASSIGNMENT_USER(HttpStatus.NOT_FOUND, "PJU001", "해당 프로젝트에 참여하지 않은 사용자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
