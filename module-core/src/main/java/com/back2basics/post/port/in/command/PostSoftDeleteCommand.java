package com.back2basics.post.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSoftDeleteCommand {

    private Long requesterId;

}
