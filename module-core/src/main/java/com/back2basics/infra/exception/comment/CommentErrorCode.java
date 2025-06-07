package com.back2basics.infra.exception.comment;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "댓글을 찾을 수 없습니다"),
    INVALID_COMMENT_AUTHOR(HttpStatus.FORBIDDEN, "P002", "댓글 작성자가 아닙니다"),
    INVALID_COMMENT_PARENT_POST_ID(HttpStatus.BAD_REQUEST, "P003", "부모 게시글 아이디가 일치하지 않습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}