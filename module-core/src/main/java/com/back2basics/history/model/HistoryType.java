package com.back2basics.history.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum HistoryType {
    CREATED("생성"),
    UPDATED("수정"),
    DELETED("삭제"),
    RESTORED("복구");

    private final String message;

    HistoryType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

