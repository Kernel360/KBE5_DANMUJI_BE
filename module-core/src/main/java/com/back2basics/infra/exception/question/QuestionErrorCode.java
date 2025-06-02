package com.back2basics.infra.exception.question;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QuestionErrorCode implements ErrorCode {
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Q001", "질문을 찾을 수 없습니다"),
    INVALID_QUESTION_AUTHOR(HttpStatus.FORBIDDEN, "Q002", "질문 작성자가 아닙니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
