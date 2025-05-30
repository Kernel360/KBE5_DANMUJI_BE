package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.command.CommentDeleteCommand;
import lombok.Getter;

@Getter
public class CommentDeleteRequest {

    // todo : 식별값 id로 변경 예정
    private Long requesterId;

    public CommentDeleteCommand toCommand() {
        return CommentDeleteCommand.builder()
            .requesterId(requesterId)
            .build();
    }
}
