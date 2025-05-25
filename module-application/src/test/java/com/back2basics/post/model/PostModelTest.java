package com.back2basics.post.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.back2basics.model.post.Post;
import com.back2basics.model.post.PostStatus;
import com.back2basics.model.post.PostType;
import com.back2basics.service.post.dto.PostUpdateCommand;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostModelTest {

    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
            .id(1L)
            .authorName("author")
            .title("title")
            .content("title")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("게시글 수정 메소드")
    void updateMethod() {
        // given
        PostUpdateCommand command = new PostUpdateCommand(
            "author",
            "new title",
            "new content",
            PostType.NOTICE,
            PostStatus.APPROVED,
            2
        );

        // when
        post.update(command);

        // then
        assertThat(post.getTitle()).isEqualTo("new title");
        assertThat(post.getContent()).isEqualTo("new content");
        assertThat(post.getType()).isEqualTo(PostType.NOTICE);
        assertThat(post.getStatus()).isEqualTo(PostStatus.APPROVED);
        assertThat(post.getPriority()).isEqualTo(2);
    }

    @Test
    @DisplayName("소프트 삭제, delete_at 필드가 null에서 수정됨")
    void softDeleteMethod() {
        // when
        post.softDelete();

        // then
        assertThat(post.getDeletedAt()).isNotNull();
    }
}
