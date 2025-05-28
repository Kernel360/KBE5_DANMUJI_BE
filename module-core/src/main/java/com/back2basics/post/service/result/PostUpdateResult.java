package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateResult {

    private final Long postId;
    private final Long requesterId;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime updatedAt;


    public static PostUpdateResult toResult(Post post) {
        return PostUpdateResult.builder()
            .postId(post.getId())
            .requesterId(post.getAuthorId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .updatedAt(post.getUpdatedAt())
            .build();
    }

}
