package com.back2basics.domain.question.controller;


import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QuestionResponseCode implements ResponseCode {

    QUESTION_CREATE_SUCCESS(HttpStatus.CREATED, "Q001", "질문 생성 성공"),
    QUESTION_READ_SUCCESS(HttpStatus.CREATED, "Q002", "질문 조회 성공"),
    QUESTION_UPDATE_SUCCESS(HttpStatus.CREATED, "Q003", "질문 수정 성공"),
    QUESTION_DELETE_SUCCESS(HttpStatus.CREATED, "Q004", "질문 삭제 성공"),
    QUESTION_MARK_AS_ANSWERED(HttpStatus.CREATED, "Q005", "질문 상태 변경 성공 - 답변완료"),
    QUESTION_MARK_AS_RESOLVED(HttpStatus.CREATED, "Q006", "질문 상태 변경 성공 - 해결됨");

    private final HttpStatus status;
    private final String code;
    private final String message;
}