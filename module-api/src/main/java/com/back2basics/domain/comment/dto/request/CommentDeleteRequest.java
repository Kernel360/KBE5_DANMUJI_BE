package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.command.CommentDeleteCommand;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDeleteRequest {

    // todo : 식별값 id로 변경 예정
    private String requesterName;

    public CommentDeleteCommand toCommand() {
        return CommentDeleteCommand.builder()
            .requsterName(requesterName)
            .build();
    }

}
