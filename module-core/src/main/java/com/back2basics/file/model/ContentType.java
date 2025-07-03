package com.back2basics.file.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    POST("게시글"),
    CHECKLIST("체크리스트"),
    ANSWER("답변");

    private final String description;
}
