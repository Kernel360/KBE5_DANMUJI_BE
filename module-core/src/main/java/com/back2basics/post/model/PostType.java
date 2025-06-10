package com.back2basics.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PostType {
    GENERAL,
    NOTICE,
    REPORT
}
