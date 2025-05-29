package com.back2basics.domain.post.dto.request;

import com.back2basics.post.port.in.command.PostSoftDeleteCommand;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostDeleteApiRequest {

    @NotNull(message = "요청자는 필수입니다.")
    private Long requesterId;

    public PostSoftDeleteCommand toCommand() {
        return PostSoftDeleteCommand.builder()
            .requesterId(requesterId)
            .build();
    }
}
