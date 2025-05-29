package com.back2basics.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.PostReadResult;
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
@DisplayName("PostReadService 테스트")
class PostReadServiceTest {

    @Mock
    private PostReadPort postReadPort;

    @Mock
    private PostValidator postValidator;

    @InjectMocks
    private PostReadService postReadService;

    private Post samplePost1;
    private Post samplePost2;

    @BeforeEach
    void setUp() {
        samplePost1 = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("첫 번째 게시글")
            .content("첫 번째 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        samplePost2 = Post.builder()
            .id(2L)
            .authorId(2L)
            .title("두 번째 게시글")
            .content("두 번째 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();
    }

    @Test
    @DisplayName("게시글 단건 조회 성공")
    void getPost_Success() {
        // given
        Long postId = 1L;
        given(postValidator.findPost(postId)).willReturn(samplePost1);

        // when
        PostReadResult result = postReadService.getPost(postId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("첫 번째 게시글");
        assertThat(result.getContent()).isEqualTo("첫 번째 내용");
        assertThat(result.getType()).isEqualTo(PostType.GENERAL);

        verify(postValidator).findPost(postId);
    }

    @Test
    @DisplayName("게시글 전체 조회 성공")
    void getPostList_Success() {
        // given
        List<Post> posts = Arrays.asList(samplePost1, samplePost2);
        given(postReadPort.findAll()).willReturn(posts);

        // when
        List<PostReadResult> results = postReadService.getPostList();

        // then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(1L);
        assertThat(results.get(0).getTitle()).isEqualTo("첫 번째 게시글");
        assertThat(results.get(1).getId()).isEqualTo(2L);
        assertThat(results.get(1).getTitle()).isEqualTo("두 번째 게시글");

        verify(postReadPort).findAll();
    }

    @Test
    @DisplayName("빈 게시글 리스트 조회")
    void getPostList_EmptyList() {
        // given
        given(postReadPort.findAll()).willReturn(Arrays.asList());

        // when
        List<PostReadResult> results = postReadService.getPostList();

        // then
        assertThat(results).isEmpty();
        verify(postReadPort).findAll();
    }
}