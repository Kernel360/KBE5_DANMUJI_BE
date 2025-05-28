package com.back2basics.service.project.exception;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectErrorCode implements ErrorCode {
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PJ001", "프로젝트를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
