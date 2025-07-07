package com.back2basics.infra.exception.project;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectErrorCode implements ErrorCode {
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PJ001", "프로젝트를 찾을 수 없습니다"),
    // todo: project_user error, response code의 위치
    PROJECT_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "PJU001", "프로젝트 유저를 찾을 수 없습니다"),
    PROJECT_ALREADY_RESTORED(HttpStatus.BAD_REQUEST, "PJU001", "이미 복구된 프로젝트입니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;
}
