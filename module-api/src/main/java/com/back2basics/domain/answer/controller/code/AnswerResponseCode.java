package com.back2basics.domain.answer.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AnswerResponseCode implements ResponseCode {

    ANSWER_CREATE_SUCCESS(HttpStatus.CREATED, "AN201", "답변 생성 완료"),
    ANSWER_DELETE_SUCCESS(HttpStatus.OK, "AN202", "답변 삭제 완료"),
    ANSWER_UPDATE_SUCCESS(HttpStatus.CREATED, "AN203", "답변 수정 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}