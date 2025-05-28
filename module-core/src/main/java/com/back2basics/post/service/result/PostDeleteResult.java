package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDeleteResult {

    private final Long id;
    private final boolean isDeleted;
    private final String title;
    private final LocalDateTime deletedAt;

    public static PostDeleteResult toResult(Post post) {
        return PostDeleteResult.builder()
            .id(post.getId())
            .title(post.getTitle())
            .deletedAt(post.getDeletedAt())
            .isDeleted(true)
            .build();
    }
}
