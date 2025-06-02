package com.back2basics.domain.answer.dto.request;

public class AnswerDeleteRequest {

    private Long requesterId;

    public AnswerDeleteCommand toCommand() {
        return AnswerDeleteCommand.builder()
            .requesterId(requesterId)
            .build();
    }
}


