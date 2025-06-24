package com.back2basics.infra.exception.checklist;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CheckListErrorCode implements ErrorCode {

    CHECK_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "체크리스트를 찾을 수 없습니다"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
