package com.back2basics.board.post.service.result;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PostCreateResult {

    private final Long id;
    private final String authorIp;
    private final Long authorId;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;
    private final Long stepId;
    private final Long projectId;


    public static PostCreateResult toResult(Post post) {
        return PostCreateResult.builder()
            .id(post.getId())
            .authorIp(post.getAuthorIp())
            .authorId(post.getAuthorId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .priority(post.getPriority())
            .createdAt(post.getCreatedAt())
            .stepId(post.getProjectStepId())
            .projectId(post.getProjectId())
            .build();
    }
}

