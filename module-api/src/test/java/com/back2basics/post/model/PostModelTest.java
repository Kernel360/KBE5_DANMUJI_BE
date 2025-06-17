package com.back2basics.post.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.back2basics.post.port.in.command.PostUpdateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Post 도메인 모델 테스트")
class PostModelTest {

    @Test
    @DisplayName("Post 생성 시 기본값이 설정된다")
    void createPost_ShouldSetDefaultValues() {
        // given
        Post post = Post.builder()
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build();

        // then
        assertThat(post.getStatus()).isEqualTo(PostPriority.PENDING);
        assertThat(post.getComments()).isEmpty();
        assertThat(post.isDelete()).isFalse();
    }

    @Test
    @DisplayName("Post 업데이트가 정상적으로 동작한다")
    void updatePost_ShouldUpdateFields() {
        // given
        Post post = Post.builder()
            .authorId(1L)
            .title("원본 제목")
            .content("원본 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build();

        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(1L)
            .title("수정된 제목")
            .content("수정된 내용")
            .type(PostType.NOTICE)
            .status(PostPriority.PENDING)
            .priority(2)
            .build();

        // when
        post.update(command);

        // then
        assertThat(post.getTitle()).isEqualTo("수정된 제목");
        assertThat(post.getContent()).isEqualTo("수정된 내용");
        assertThat(post.getType()).isEqualTo(PostType.NOTICE);
        assertThat(post.getStatus()).isEqualTo(PostPriority.PENDING);
        assertThat(post.getPriority()).isEqualTo(2);
    }

    @Test
    @DisplayName("Post 삭제 표시가 정상적으로 동작한다")
    void markDeleted_ShouldSetDeleteFlag() {
        // given
        Post post = Post.builder()
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build();

        // when
        post.markDeleted();

        // then
        assertThat(post.isDelete()).isTrue();
    }
}