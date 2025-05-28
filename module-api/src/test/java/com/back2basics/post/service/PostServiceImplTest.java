package com.back2basics.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.port.out.PostSoftDeletePort;
import com.back2basics.post.port.out.PostUpdatePort;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostDeleteResult;
import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.post.service.result.PostSimpleResult;
import com.back2basics.post.service.result.PostUpdateResult;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostService 테스트")
class PostServiceImplTest {

    @Mock
    private PostCreatePort postCreatePort;

    @Mock
    private PostReadPort postReadPort;

    @Mock
    private PostUpdatePort postUpdatePort;

    @Mock
    private PostSoftDeletePort postSoftDeletePort;

    @Mock
    private PostValidator postValidator;

    @InjectMocks
    private PostServiceImpl postService;

    private Post samplePost;
    private Post samplePost2;

    @BeforeEach
    void setUp() {
        samplePost = Post.builder()
            .id(1L)
            .authorId(1L)  // authorName -> authorId로 변경
            .title("title")
            .content("content")
            .type(PostType.GENERAL)  // type 필드 추가
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        samplePost2 = Post.builder()
            .id(2L)
            .authorId(2L)  // authorName -> authorId로 변경
            .title("title2")
            .content("content2")
            .type(PostType.NOTICE)  // type 필드 추가
            .status(PostStatus.PENDING)  // APPROVED -> PENDING으로 변경 (존재하지 않는 상태)
            .priority(2)
            .build();
    }

    @Test
    @DisplayName("게시글 생성")
    void createPostTest() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
            .authorId(1L)
            .title("title")
            .content("content")
            .type(PostType.GENERAL)
            .priority(1)
            .build();

        // when
        PostCreateResult result = postService.createPost(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        verify(postCreatePort).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void getPostTest() {
        // given
        given(postValidator.findPost(1L)).willReturn(samplePost);

        // when
        PostDetailsResult result = postService.getPost(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(samplePost.getId());
        verify(postValidator).findPost(1L);
    }

    @Test
    @DisplayName("게시글 리스트 조회")
    void getPostListTest() {
        // given
        List<Post> posts = Arrays.asList(samplePost, samplePost2);
        given(postReadPort.findAll()).willReturn(posts);

        // when
        List<PostSimpleResult> resultList = postService.getPostList();

        // then
        assertThat(resultList).hasSize(2);
        verify(postReadPort).findAll();
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePostTest() {
        // given
        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(1L)
            .title("new title")
            .content("new content")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(3)
            .build();

        given(postValidator.findPost(1L)).willReturn(samplePost);
        willDoNothing().given(postValidator).isAuthor(samplePost, 1L);

        // when
        PostUpdateResult result = postService.updatePost(1L, command);

        // then
        assertThat(result).isNotNull();
        assertThat(samplePost.getTitle()).isEqualTo("new title");
        verify(postValidator).findPost(1L);
        verify(postValidator).isAuthor(samplePost, 1L);
        verify(postUpdatePort).update(samplePost);
    }

    @Test
    @DisplayName("게시글 수정 실패 - 작성자 아님")
    void updatePostFail_NotAuthor() {
        // given
        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(2L)  // 다른 사용자 ID
            .title("new title")
            .content("new content")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(3)
            .build();

        given(postValidator.findPost(1L)).willReturn(samplePost);
        willThrow(new PostException(PostErrorCode.INVALID_POST_AUTHOR))
            .given(postValidator).isAuthor(samplePost, 2L);

        // when & then
        assertThatThrownBy(() -> postService.updatePost(1L, command))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.INVALID_POST_AUTHOR.getMessage());

        verify(postValidator).isAuthor(samplePost, 2L);
        verify(postUpdatePort, never()).update(any());
    }

    @Test
    @DisplayName("게시글 삭제(소프트 삭제)")
    void softDeletePostTest() {
        // given
        Long requesterId = 1L;
        given(postValidator.findPost(1L)).willReturn(samplePost);
        willDoNothing().given(postValidator).isAuthor(samplePost, requesterId);

        // when
        PostDeleteResult result = postService.softDeletePost(1L, requesterId);

        // then
        assertThat(result).isNotNull();
        assertThat(samplePost.isDelete()).isTrue();
        verify(postValidator).findPost(1L);
        verify(postValidator).isAuthor(samplePost, requesterId);
        verify(postSoftDeletePort).softDelete(samplePost);
    }

    @Test
    @DisplayName("게시글 삭제(소프트 삭제) 실패 - 작성자 아님")
    void softDeletePostFail_NotAuthor() {
        // given
        Long requesterId = 2L;  // 다른 사용자 ID
        given(postValidator.findPost(1L)).willReturn(samplePost);
        willThrow(new PostException(PostErrorCode.INVALID_POST_AUTHOR))
            .given(postValidator).isAuthor(samplePost, requesterId);

        // when & then
        assertThatThrownBy(() -> postService.softDeletePost(1L, requesterId))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.INVALID_POST_AUTHOR.getMessage());

        verify(postValidator).isAuthor(samplePost, requesterId);
        verify(postSoftDeletePort, never()).softDelete(any());
    }
}