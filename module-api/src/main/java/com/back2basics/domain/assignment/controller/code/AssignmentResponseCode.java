package com.back2basics.domain.assignment.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AssignmentResponseCode implements ResponseCode {

    ASSIGNMENT_COMPANY_DELETE_SUCCESS(HttpStatus.OK, "A201", "해당 프로젝트의 할당된 업체 삭제 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
