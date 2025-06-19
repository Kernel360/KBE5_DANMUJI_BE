package com.back2basics.infra.exception.inquiry;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InquiryErrorCode implements ErrorCode {

    INQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, "INQ001", "문의사항을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
