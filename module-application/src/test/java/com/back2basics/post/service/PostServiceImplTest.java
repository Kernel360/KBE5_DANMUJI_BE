package com.back2basics.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.back2basics.infra.validation.PostValidator;
import com.back2basics.model.post.Post;
import com.back2basics.model.post.PostStatus;
import com.back2basics.port.out.post.PostRepositoryPort;
import com.back2basics.service.post.PostServiceImpl;
import com.back2basics.service.post.dto.PostCreateCommand;
import com.back2basics.service.post.dto.PostResponseDto;
import com.back2basics.service.post.dto.PostUpdateCommand;
import com.back2basics.service.post.exception.PostErrorCode;
import com.back2basics.service.post.exception.PostException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepositoryPort postRepository;

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
            .authorName("author")
            .title("title")
            .content("content")
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        samplePost2 = Post.builder()
            .id(2L)
            .authorName("author2")
            .title("title2")
            .content("content2")
            .status(PostStatus.APPROVED)
            .priority(2)
            .build();
    }

    @Test
    @DisplayName("게시글 생성")
    void createPostTest() {
        // given
        PostCreateCommand command = new PostCreateCommand("author", "title", "content",
            samplePost.getType(), 1);

        // when
        when(postRepository.save(any(Post.class))).thenReturn(1L);

        // then
        Long id = postService.createPost(command);

        assertThat(id).isEqualTo(1L);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void findPostByIdTest() {
        // when
        when(postValidator.findPost(1L)).thenReturn(samplePost);

        // then
        PostResponseDto result = postService.getPost(1L);

        assertThat(result.getId()).isEqualTo(samplePost.getId());
        verify(postValidator).findPost(1L);
    }

    @Test
    @DisplayName("게시글 리스트 조회")
    void findAllPostsTest() {
        // when
        when(postRepository.findAll()).thenReturn(List.of(samplePost, samplePost2));

        // then
        List<PostResponseDto> resultList = postService.getAllPosts();

        assertThat(resultList).hasSize(2);
        verify(postRepository).findAll();
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePostTest() {
        // given
        PostUpdateCommand command = new PostUpdateCommand("author", "new title", "new content",
            samplePost.getType(), samplePost.getStatus(), 3);

        // when
        when(postValidator.findPost(1L)).thenReturn(samplePost);
        doNothing().when(postValidator).isAuthor(samplePost, "author");

        postService.updatePost(1L, command);

        // then
        assertThat(samplePost.getTitle()).isEqualTo("new title");

        verify(postValidator).findPost(1L);
        verify(postValidator).isAuthor(samplePost, "author");
        verify(postRepository).update(samplePost);

    }

    @Test
    @DisplayName("게시글 수정 실패 - 작성자 아님")
    void updatePostFail_NotAuthor() {
        // given
        PostUpdateCommand command = new PostUpdateCommand("aaa", "new title", "new content",
            samplePost.getType(), samplePost.getStatus(), 5);

        // when
        when(postValidator.findPost(1L)).thenReturn(samplePost);
        doThrow(new PostException(PostErrorCode.INVALID_POST_AUTHOR)).when(postValidator)
            .isAuthor(samplePost, "aaa");

        assertThatThrownBy(() -> postService.updatePost(1L, command))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.INVALID_POST_AUTHOR.getMessage());

        // then
        verify(postValidator).isAuthor(samplePost, "aaa");
        verify(postRepository, never()).update(any());
    }

    @Test
    @DisplayName("게시글 삭제(소프트 삭제)")
    void softDeletePostTest() {
        // when
        when(postValidator.findPost(1L)).thenReturn(samplePost);
        doNothing().when(postValidator).isAuthor(samplePost, "author");
        doNothing().when(postRepository).softDelete(samplePost);

        postService.softDeletePost(1L, "author");

        // then
        verify(postValidator).findPost(1L);
        verify(postValidator).isAuthor(samplePost, "author");
        verify(postRepository).softDelete(samplePost);
    }

    @Test
    @DisplayName("게시글 삭제(소프트 삭제) 실패 - 작성자 아님")
    void softDeletePostFail_NotAuthor() {
        // given
        when(postValidator.findPost(1L)).thenReturn(samplePost);
        doThrow(new PostException(PostErrorCode.INVALID_POST_AUTHOR)).when(postValidator)
            .isAuthor(samplePost, "aaa");

        // then
        assertThatThrownBy(() -> postService.softDeletePost(1L, "aaa"))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.INVALID_POST_AUTHOR.getMessage());

        verify(postValidator).isAuthor(samplePost, "aaa");
        verify(postRepository, never()).softDelete(any());
    }
}
