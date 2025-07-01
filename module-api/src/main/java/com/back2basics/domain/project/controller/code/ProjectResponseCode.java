package com.back2basics.domain.project.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectResponseCode implements ResponseCode {

    PROJECT_CREATE_SUCCESS(HttpStatus.CREATED, "PJ201", "프로젝트 생성 완료"),
    PROJECT_READ_SUCCESS(HttpStatus.OK, "PJ202", "프로젝트 상세 정보 조회 완료"),
    ASSIGNMENT_PROJECT_READ_SUCCESS(HttpStatus.OK, "PJ203", " 할당 프로젝트 상세 조회 완료"),
    PROJECT_READ_ALL_SUCCESS(HttpStatus.OK, "PJ204", "프로젝트 목록 조회 완료"),
    ASSIGNMENT_PROJECT_READ_ALL_SUCCESS(HttpStatus.OK, "PJ205", "할당 프로젝트 목록 조회 완료"),
    PROJECT_UPDATE_SUCCESS(HttpStatus.OK, "PJ206", "프로젝트 수정 완료"),
    PROJECT_DELETE_SUCCESS(HttpStatus.OK, "PJ207", "프로젝트 삭제 완료"),
    DELETED_PROJECT_READ_ALL_SUCCESS(HttpStatus.OK, "PJ208", "삭제 프로젝트 목록 조회 완료"),
    PROJECT_READ_BY_STATUS_SUCCESS(HttpStatus.OK, "PJ209", "프로젝트 상태에 따른 목록 조회 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
