package com.back2basics.service.post.exception;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "게시글을 찾을 수 없습니다"),
    INVALID_POST_AUTHOR(HttpStatus.FORBIDDEN, "P002", "게시글 작성자가 아닙니다"),
    DUPLICATE_POST_TITLE(HttpStatus.BAD_REQUEST, "P003", "중복된 제목의 게시글입니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}