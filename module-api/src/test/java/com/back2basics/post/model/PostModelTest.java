package com.back2basics.post.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.back2basics.comment.model.Comment;
import com.back2basics.post.port.in.command.PostUpdateCommand;
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
    @DisplayName("댓글이 게시글에 잘 연결되는지 확인")
    void addCommentMethod() {
        // given
        Comment comment = new Comment(1L, 1L, null, "comment author", "content",
            LocalDateTime.now(), LocalDateTime.now(), null);

        // when
        post.addComment(comment);

        // then
        assertThat(post.getComments().size()).isEqualTo(1);
        assertThat(post.getComments().get(0)).isEqualTo(comment);
        assertThat(comment.getPostId()).isEqualTo(post.getId());
    }

}
