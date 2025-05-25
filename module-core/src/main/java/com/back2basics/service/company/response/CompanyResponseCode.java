package com.back2basics.service.company.response;

import com.back2basics.response.global.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyResponseCode implements ResponseCode {

    COMPANY_CREATE_SUCCESS(HttpStatus.CREATED, "COMP201", "회사 생성 완료"),
    COMPANY_READ_SUCCESS(HttpStatus.OK, "COMP202", "회사 조회 완료"),
    COMPANY_READ_ALL_SUCCESS(HttpStatus.OK, "COMP203", "회사 목록 조회 완료"),
    COMPANY_UPDATE_SUCCESS(HttpStatus.OK, "COMP204", "회사 수정 완료"),
    COMPANY_DELETE_SUCCESS(HttpStatus.OK, "COMP205", "회사 삭제 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
