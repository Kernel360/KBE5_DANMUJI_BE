package com.back2basics.domain.post.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostResponseCode implements ResponseCode {

    POST_CREATE_SUCCESS(HttpStatus.CREATED, "P201", "게시글 생성 완료"),
    POST_READ_SUCCESS(HttpStatus.OK, "P202", "게시글 조회 완료"),
    POST_READ_ALL_SUCCESS(HttpStatus.OK, "P203", "게시글 목록 조회 완료"),
    POST_UPDATE_SUCCESS(HttpStatus.OK, "P204", "게시글 수정 완료"),
    POST_DELETE_SUCCESS(HttpStatus.OK, "P205", "게시글 삭제 완료"),

    POST_FILE_DOWNLOAD_SUCCESS(HttpStatus.OK, "PF206", "파일 다운로드 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

