package com.back2basics.infra.exception.projectstep;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectStepErrorCode implements ErrorCode {

    STEP_NOT_FOUND(HttpStatus.NOT_FOUND, "PS001", "프로젝트 단계를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
