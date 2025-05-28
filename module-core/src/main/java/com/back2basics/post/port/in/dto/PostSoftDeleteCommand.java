package com.back2basics.post.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSoftDeleteCommand {

    private Long requesterId;

}
