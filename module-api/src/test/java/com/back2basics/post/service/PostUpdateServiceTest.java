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
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostUpdatePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostUpdateService 테스트")
class PostUpdateServiceTest {

    @Mock
    private PostUpdatePort postUpdatePort;

    @Mock
    private PostValidator postValidator;

    @InjectMocks
    private PostUpdateService postUpdateService;

    private Post samplePost;

    @BeforeEach
    void setUp() {
        samplePost = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("원본 제목")
            .content("원본 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void updatePost_Success() {
        // given
        Long postId = 1L;
        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(1L)
            .title("수정된 제목")
            .content("수정된 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();

        given(postValidator.findPost(postId)).willReturn(samplePost);
        willDoNothing().given(postValidator).isAuthor(samplePost, 1L);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        assertThat(samplePost.getTitle()).isEqualTo("수정된 제목");
        assertThat(samplePost.getContent()).isEqualTo("수정된 내용");
        assertThat(samplePost.getType()).isEqualTo(PostType.NOTICE);
        assertThat(samplePost.getPriority()).isEqualTo(2);

        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(samplePost, 1L);
        verify(postUpdatePort).update(samplePost);
    }

    @Test
    @DisplayName("게시글 수정 실패 - 작성자가 아님")
    void updatePost_NotAuthor_ShouldThrowException() {
        // given
        Long postId = 1L;
        Long unauthorizedUserId = 2L;
        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(unauthorizedUserId)
            .title("수정된 제목")
            .content("수정된 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();

        given(postValidator.findPost(postId)).willReturn(samplePost);
        willThrow(new PostException(PostErrorCode.INVALID_POST_AUTHOR))
            .given(postValidator).isAuthor(samplePost, unauthorizedUserId);

        // when & then
        assertThatThrownBy(() -> postUpdateService.updatePost(postId, command))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.INVALID_POST_AUTHOR.getMessage());

        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(samplePost, unauthorizedUserId);
        verify(postUpdatePort, never()).update(samplePost);
    }

    @Test
    @DisplayName("게시글 부분 필드만 수정")
    void updatePost_PartialUpdate() {
        // given
        Long postId = 1L;
        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(1L)
            .title("새로운 제목만 변경")
            .content("원본 내용")  // 기존과 동일
            .type(PostType.GENERAL)  // 기존과 동일
            .status(PostStatus.PENDING)
            .priority(1)  // 기존과 동일
            .build();

        given(postValidator.findPost(postId)).willReturn(samplePost);
        willDoNothing().given(postValidator).isAuthor(samplePost, 1L);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        assertThat(samplePost.getTitle()).isEqualTo("새로운 제목만 변경");
        assertThat(samplePost.getContent()).isEqualTo("원본 내용");
        assertThat(samplePost.getType()).isEqualTo(PostType.GENERAL);
        assertThat(samplePost.getPriority()).isEqualTo(1);

        verify(postUpdatePort).update(samplePost);
    }
}