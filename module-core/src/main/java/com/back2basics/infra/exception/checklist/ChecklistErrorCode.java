package com.back2basics.infra.exception.checklist;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChecklistErrorCode implements ErrorCode {

    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "체크리스트를 찾을 수 없습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "A002", "권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}