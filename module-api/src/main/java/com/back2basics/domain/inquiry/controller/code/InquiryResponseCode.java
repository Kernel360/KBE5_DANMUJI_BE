package com.back2basics.domain.inquiry.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InquiryResponseCode implements ResponseCode {

    INQUIRY_CREATE_SUCCESS(HttpStatus.CREATED, "INQ201", "문의사항 생성 완료"),
    INQUIRY_READ_SUCCESS(HttpStatus.OK, "INQ202", "문의사항 조회 완료"),
    INQUIRY_READ_ALL_SUCCESS(HttpStatus.OK, "INQ203", "문의사항 목록 조회 완료"),
    INQUIRY_UPDATE_SUCCESS(HttpStatus.OK, "INQ204", "문의사항 수정 완료"),
    INQUIRY_DELETE_SUCCESS(HttpStatus.OK, "INQ205", "문의사항 삭제 완료"),
    INQUIRY_SEARCH_SUCCESS(HttpStatus.OK, "INQ206", "문의사항 검색 완료"),
    INQUIRY_USER_LIST_SUCCESS(HttpStatus.OK, "INQ207", "문의사항 목록 전체 조회 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
