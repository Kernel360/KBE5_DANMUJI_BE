package com.back2basics.domain.question.dto.request;


import lombok.Getter;

@Getter
public class QuestionUpdateRequest {

    private Long requesterId;
    private String content;
}
