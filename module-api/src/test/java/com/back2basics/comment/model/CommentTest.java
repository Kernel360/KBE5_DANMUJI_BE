package com.back2basics.comment.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    @DisplayName("댓글 생성 시 모든 필드가 올바르게 설정된다")
    void createComment_Success() {
        // given
        Long authorId = 1L;
        String content = "테스트 댓글입니다.";
        LocalDateTime now = LocalDateTime.now();

        // when
        Comment comment = Comment.builder()
            .id(1L)
            .postId(1L)
            .authorId(authorId)
            .content(content)
            .createdAt(now)
            .build();

        // then
        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getAuthorId()).isEqualTo(authorId);
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCreatedAt()).isEqualTo(now);
        assertThat(comment.getChildren()).isEmpty();
    }

    @Test
    @DisplayName("댓글 내용을 업데이트할 수 있다")
    void updateComment_Success() {
        // given
        Comment comment = Comment.builder()
            .id(1L)
            .authorId(1L)
            .content("원본 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentUpdateCommand command = CommentUpdateCommand.builder()
            .requesterId(1L)
            .content("수정된 댓글")
            .build();

        // when
        comment.update(command);

        // then
        assertThat(comment.getContent()).isEqualTo("수정된 댓글");
    }

    @Test
    @DisplayName("게시글을 댓글에 할당할 수 있다")
    void assignPost_Success() {
        // given
        Comment comment = Comment.builder()
            .id(1L)
            .authorId(1L)
            .content("테스트 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        Post post = Post.builder()
            .id(2L)
            .authorId(1L)
            .title("테스트 게시글")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        // when
        comment.assignPostId(post);

        // then
        assertThat(comment.getPostId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("부모 댓글을 할당할 수 있다")
    void assignParent_Success() {
        // given
        Comment parentComment = Comment.builder()
            .id(1L)
            .authorId(1L)
            .content("부모 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        Comment childComment = Comment.builder()
            .id(2L)
            .authorId(2L)
            .content("자식 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        childComment.assignParent(parentComment);

        // then
        assertThat(childComment.getParentCommentId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("자식 댓글을 추가할 수 있다")
    void addChild_Success() {
        // given
        Comment parentComment = Comment.builder()
            .id(1L)
            .authorId(1L)
            .content("부모 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        Comment childComment = Comment.builder()
            .id(2L)
            .authorId(2L)
            .content("자식 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        parentComment.addChild(childComment);

        // then
        assertThat(parentComment.getChildren()).hasSize(1);
        assertThat(parentComment.getChildren().get(0)).isEqualTo(childComment);
        assertThat(childComment.getParentCommentId()).isEqualTo(1L);
    }
}