package com.back2basics.question.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum QuestionStatus {
    WAITING,
    ANSWERED,
    RESOLVED,
    UNRESOLVED
}
