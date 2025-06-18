package com.back2basics.domain.projectstep.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectStepResponseCode implements ResponseCode {

    STEP_CREATE_SUCCESS(HttpStatus.CREATED, "PS201", "프로젝트 단계 생성"),
    STEP_UPDATE_SUCCESS(HttpStatus.OK, "PS202", "프로젝트 단계 수정 완료"),
    STEP_DELETE_SUCCESS(HttpStatus.OK, "PJS203", "프로젝트 단계 삭제 완료"),
    STEP_ALL_READ_SUCCESS(HttpStatus.OK, "PJS204", "프로젝트 단계 목록 조회 완료"),
    STEP_READ_SUCCESS(HttpStatus.OK, "PJS205", "프로젝트 단계 상세 조회 완료"),
    STEP_APPROVAL_SUCCESS(HttpStatus.OK, "PJS206", "프로젝트 단계 승인 요청 완료");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
