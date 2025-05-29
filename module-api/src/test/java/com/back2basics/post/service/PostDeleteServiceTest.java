package com.back2basics.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.out.PostSoftDeletePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostDeleteService 테스트")
class PostDeleteServiceTest {

    @Mock
    private PostSoftDeletePort postSoftDeletePort;

    @Mock
    private PostValidator postValidator;

    @InjectMocks
    private PostDeleteService postDeleteService;

    private Post samplePost;

    @BeforeEach
    void setUp() {
        samplePost = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("삭제할 게시글")
            .content("삭제할 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();
    }

    @Test
    @DisplayName("게시글 소프트 삭제 성공")
    void softDeletePost_Success() {
        // given
        Long postId = 1L;
        Long requesterId = 1L;

        given(postValidator.findPost(postId)).willReturn(samplePost);
        willDoNothing().given(postValidator).isAuthor(samplePost, requesterId);

        // when
        postDeleteService.softDeletePost(postId, requesterId);

        // then
        assertThat(samplePost.isDelete()).isTrue();

        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(samplePost, requesterId);
        verify(postSoftDeletePort).softDelete(samplePost);
    }

    @Test
    @DisplayName("게시글 소프트 삭제 실패 - 작성자가 아님")
    void softDeletePost_NotAuthor_ShouldThrowException() {
        // given
        Long postId = 1L;
        Long unauthorizedUserId = 2L;

        given(postValidator.findPost(postId)).willReturn(samplePost);
        willThrow(new PostException(PostErrorCode.INVALID_POST_AUTHOR))
            .given(postValidator).isAuthor(samplePost, unauthorizedUserId);

        // when & then
        assertThatThrownBy(() -> postDeleteService.softDeletePost(postId, unauthorizedUserId))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.INVALID_POST_AUTHOR.getMessage());

        assertThat(samplePost.isDelete()).isFalse();  // 삭제되지 않았는지 확인

        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(samplePost, unauthorizedUserId);
        verify(postSoftDeletePort, never()).softDelete(samplePost);
    }

    @Test
    @DisplayName("이미 삭제된 게시글도 소프트 삭제 가능")
    void softDeletePost_AlreadyDeleted() {
        // given
        Long postId = 1L;
        Long requesterId = 1L;
        samplePost.markDeleted();  // 이미 삭제된 상태로 설정

        given(postValidator.findPost(postId)).willReturn(samplePost);
        willDoNothing().given(postValidator).isAuthor(samplePost, requesterId);

        // when
        postDeleteService.softDeletePost(postId, requesterId);

        // then
        assertThat(samplePost.isDelete()).isTrue();

        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(samplePost, requesterId);
        verify(postSoftDeletePort).softDelete(samplePost);
    }
}