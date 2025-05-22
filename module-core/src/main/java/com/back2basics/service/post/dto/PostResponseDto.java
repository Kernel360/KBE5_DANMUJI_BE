package com.back2basics.service.post.dto;

import com.back2basics.model.post.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String authorName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt; 

    @Builder
    public PostResponseDto(Long id, String authorName, String title, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
            .id(post.getId())
            .authorName(post.getAuthorName())
            .title(post.getTitle())
            .content(post.getContent())
            //.createdAt(post.getCreatedAt())
            //.updatedAt(post.getUpdatedAt())
            .build();
    }
}
