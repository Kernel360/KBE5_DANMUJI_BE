package com.back2basics.domain.projectstep.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectStepResponseCode implements ResponseCode {

    STEP_CREATE_SUCCESS(HttpStatus.CREATED, "PS201", "프로젝트 단계 생성"),
    STEP_UPDATE_SUCCESS(HttpStatus.OK, "PJ204", "프로젝트 수정 완료"),
    STEP_DELETE_SUCCESS(HttpStatus.OK, "PJ205", "프로젝트 삭제 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
