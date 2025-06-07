package com.back2basics.domain.comment.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentResponseCode implements ResponseCode {

    COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "C201", "댓글 생성 완료"),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "C202", "댓글 삭제 완료"),
    COMMENT_UPDATE_SUCCESS(HttpStatus.CREATED, "C203", "댓글 수정 완료"),
    COMMENT_READ_SUCCESS(HttpStatus.OK, "C204", "댓글 목록 조회 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}