package com.back2basics.board.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PostPriority {
    LOW("낮음"),
    MEDIUM("보통"),
    HIGH("높음"),
    URGENT("긴급");

    private final String label;

    PostPriority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}