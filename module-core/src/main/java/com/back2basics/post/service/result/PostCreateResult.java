package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
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
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;


    public static PostCreateResult toResult(Post post) {
        return PostCreateResult.builder()
            .id(post.getId())
            .authorIp(post.getAuthorIp())
            .authorId(post.getAuthor().getId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .createdAt(post.getCreatedAt())
            .build();
    }
}

