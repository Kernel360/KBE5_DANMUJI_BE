package com.back2basics.domain.checklist.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CheckListResponseCode implements ResponseCode {

    CHECK_LIST_CREATE_SUCCESS(HttpStatus.CREATED, "C201", "체크리스트 생성 완료"),
    CHECK_LIST_READ_SUCCESS(HttpStatus.OK, "C202", "체크리스트 조회 완료"),
    CHECK_LIST_READ_ALL_SUCCESS(HttpStatus.OK, "C203", "체크리스트 목록 조회 완료"),
    CHECK_LIST_UPDATE_SUCCESS(HttpStatus.OK, "C204", "체크리스트 수정 완료"),
    CHECK_LIST_DELETE_SUCCESS(HttpStatus.OK, "C205", "체크리스트 삭제 완료"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
