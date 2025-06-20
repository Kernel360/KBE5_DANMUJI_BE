package com.back2basics.domain.board.dto.response;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.service.result.PostCreateResult;
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
    private final PostPriority priority;
    private final Long stepId;
    private final Long projectId;

    public static PostCreateResponse toResponse(PostCreateResult result) {
        return PostCreateResponse.builder()
            .authorIp(result.getAuthorIp())
            .authorId(result.getAuthorId())
            .title(result.getTitle())
            .content(result.getContent())
            .type(result.getType())
            .priority(result.getPriority())
            .stepId(result.getStepId())
            .projectId(result.getProjectId())
            .build();
    }


}
