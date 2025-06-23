package com.back2basics.history.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum HistoryType {
    POST_CREATED("게시글 생성"),
    POST_UPDATED("게시글 수정"),
    POST_DELETED("게시글 삭제");

    private final String message;

    HistoryType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

