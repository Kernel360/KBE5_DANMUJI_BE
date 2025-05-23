package com.back2basics.service.post.exception;

import com.back2basics.response.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(404, "P001", "게시글을 찾을 수 없습니다"),
    UNAUTHORIZED_AUTHOR(403, "P002", "게시글 작성자가 아닙니다"),
    DUPLICATE_POST_TITLE(400, "P003", "중복된 제목의 게시글입니다");

    private final int status;
    private final String code;
    private final String message;
}