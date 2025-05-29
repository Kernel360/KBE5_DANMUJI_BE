package com.back2basics.domain.project.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectResponseCode implements ResponseCode {

    PROJECT_CREATE_SUCCESS(HttpStatus.CREATED, "PJ201", "프로젝트 생성 완료"),
    PROJECT_READ_SUCCESS(HttpStatus.OK, "PJ202", "프로젝트 조회 완료"),
    PROJECT_READ_ALL_SUCCESS(HttpStatus.OK, "PJ203", "프로젝트 목록 조회 완료"),
    PROJECT_UPDATE_SUCCESS(HttpStatus.OK, "PJ204", "프로젝트 수정 완료"),
    PROJECT_DELETE_SUCCESS(HttpStatus.OK, "PJ205", "프로젝트 삭제 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
