package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadRecentPostResult {

    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;

    @Builder
    public ReadRecentPostResult(Long id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public static ReadRecentPostResult toResult(Post post) {
        return ReadRecentPostResult.builder()
            .id(post.getId())
            .title(post.getTitle())
            .createdAt(post.getCreatedAt())
            .build();
    }

}
