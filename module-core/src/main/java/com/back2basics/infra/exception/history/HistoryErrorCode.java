package com.back2basics.infra.exception.history;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HistoryErrorCode implements ErrorCode {
    HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "H001", "이력을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
