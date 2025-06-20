package com.back2basics.board.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PostType {
    GENERAL("일반"),
    QUESTION("질문");

    private final String label;

    PostType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
