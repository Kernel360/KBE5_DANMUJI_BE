package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostCreateResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCreateResponse {

    private final String authorIp;
    private final Long authorId;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;

    public static PostCreateResponse toResponse(PostCreateResult result) {
        return PostCreateResponse.builder()
            .authorIp(result.getAuthorIp())
            .authorId(result.getAuthorId())
            .title(result.getTitle())
            .content(result.getContent())
            .type(result.getType())
            .status(result.getStatus())
            .priority(result.getPriority())
            .build();
    }


}
