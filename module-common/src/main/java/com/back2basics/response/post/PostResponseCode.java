package com.back2basics.response.post;

import com.back2basics.response.global.result.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostResponseCode implements ResponseCode {

    POST_CREATE_SUCCESS(201, "P201", "게시글 생성 완료"),
    POST_READ_SUCCESS(200, "P202", "게시글 조회 완료"),
    POST_READ_ALL_SUCCESS(200, "P203", "게시글 목록 조회 완료"),
    POST_UPDATE_SUCCESS(200, "P204", "게시글 수정 완료"),
    POST_DELETE_SUCCESS(200, "P205", "게시글 삭제 완료");

    private final int status;
    private final String code;
    private final String message;
}
