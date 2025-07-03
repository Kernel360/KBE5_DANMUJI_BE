package com.back2basics.domain.checklist.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChecklistResponseCode implements ResponseCode {

    CHECKLIST_REQUEST_CREATE_SUCCESS(HttpStatus.CREATED, "A201", "체크리스트 생성 완료"),
    CHECKLIST_REQUEST_READ_SUCCESS(HttpStatus.OK, "A202", "체크리스트 조회 완료"),
    CHECKLIST_REQUEST_READ_ALL_SUCCESS(HttpStatus.OK, "A203", "체크리스트 목록 조회 완료"),
    CHECKLIST_REQUEST_UPDATE_SUCCESS(HttpStatus.OK, "A204", "체크리스트 수정 완료"),
    CHECKLIST_REQUEST_DELETE_SUCCESS(HttpStatus.OK, "A205", "체크리스트 삭제 완료"),
    CHECKLIST_RESPONSE_READ_SUCCESS(HttpStatus.OK, "A206", "승인 요청에 대한 정보 조회 완료"),
    CHECKLIST_RESPONSE_STATUS_READ_SUCCESS(HttpStatus.OK, "A207", "승인 요청 상태 조회 완료"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}