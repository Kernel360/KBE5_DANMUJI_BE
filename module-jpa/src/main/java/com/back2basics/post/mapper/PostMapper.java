package com.back2basics.post.mapper;

import com.back2basics.model.post.Post;
import com.back2basics.post.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toDomain(PostEntity entity) {
        return Post.builder()
            .id(entity.getId())
            .authorName(entity.getAuthorName())
            .title(entity.getTitle())
            .content(entity.getContent())
            .build();
    }

    public PostEntity fromDomain(Post domain) {
        return PostEntity.builder()
            .id(domain.getId())
            .authorName(domain.getAuthorName())
            .title(domain.getTitle())
            .content(domain.getContent())
            .build();
    }
}