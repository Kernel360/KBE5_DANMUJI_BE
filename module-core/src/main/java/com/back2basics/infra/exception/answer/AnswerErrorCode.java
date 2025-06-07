package com.back2basics.infra.exception.answer;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AnswerErrorCode implements ErrorCode {

    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "AN001", "답변을 찾을 수 없습니다"),
    INVALID_ANSWER_AUTHOR(HttpStatus.FORBIDDEN, "AN002", "답변 작성자가 아닙니다"),
    INVALID_ANSWER_PARENT_QUESTION_ID(HttpStatus.BAD_REQUEST, "AN003", "부모 질문 아이디가 일치하지 않습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}