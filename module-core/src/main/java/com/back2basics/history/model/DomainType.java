package com.back2basics.history.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DomainType {
    POST("게시글"),
    USER("회원"),
    PROJECT("프로젝트"),
    COMPANY("업체"),
    STEP("단계"),
    CHECKLIST("체크리스트"),
    INQUIRY("관리자 문의"),
    TODOLIST("할일리스트");

    private final String message;

    DomainType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
