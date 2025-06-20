package com.back2basics.domain.approval.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApprovalResponseCode implements ResponseCode {

    APPROVAL_REQUEST_CREATE_SUCCESS(HttpStatus.CREATED, "A201", "승인 요청 생성 완료"),
    APPROVAL_REQUEST_READ_SUCCESS(HttpStatus.OK, "A202", "승인 요청 조회 완료"),
    APPROVAL_REQUEST_READ_ALL_SUCCESS(HttpStatus.OK, "A203", "승인 요청 목록 조회 완료"),
    APPROVAL_REQUEST_UPDATE_SUCCESS(HttpStatus.OK, "A204", "승인 요청 수정 완료"),
    APPROVAL_REQUEST_DELETE_SUCCESS(HttpStatus.OK, "A205", "승인 요청 삭제 완료"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}