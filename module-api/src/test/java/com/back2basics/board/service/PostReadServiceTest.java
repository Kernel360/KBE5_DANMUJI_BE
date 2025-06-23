package com.back2basics.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.board.post.service.PostReadService;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.out.PostReadPort;
import com.back2basics.board.post.service.result.PostDetailReadResult;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
            .status(PostPriority.PENDING)
            .priority(1)
            .build();

        samplePost2 = Post.builder()
            .id(2L)
            .authorId(2L)
            .title("두 번째 게시글")
            .content("두 번째 내용")
            .type(PostType.NOTICE)
            .status(PostPriority.PENDING)
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
        PostDetailReadResult result = postReadService.getPost(postId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("첫 번째 게시글");
        assertThat(result.getContent()).isEqualTo("첫 번째 내용");
        assertThat(result.getType()).isEqualTo(PostType.GENERAL);

        verify(postValidator).findPost(postId);
    }

    @Test
    @DisplayName("게시글 페이징 조회 성공")
    void getPostList_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = Arrays.asList(samplePost1, samplePost2);
        Page<Post> postPage = new PageImpl<>(posts, pageable, 2);

        given(postReadPort.findAllWithPaging(pageable)).willReturn(postPage);

        // when
        Page<PostDetailReadResult> results = postReadService.getPostList(pageable);

        // then
        assertThat(results).isNotNull();
        assertThat(results.getContent()).hasSize(2);
        assertThat(results.getTotalElements()).isEqualTo(2);
        assertThat(results.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(results.getContent().get(0).getTitle()).isEqualTo("첫 번째 게시글");
        assertThat(results.getContent().get(1).getId()).isEqualTo(2L);
        assertThat(results.getContent().get(1).getTitle()).isEqualTo("두 번째 게시글");

        verify(postReadPort).findAllWithPaging(pageable);
    }

    @Test
    @DisplayName("빈 게시글 페이지 조회")
    void getPostList_EmptyPage() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> emptyPage = new PageImpl<>(Arrays.asList(), pageable, 0);

        given(postReadPort.findAllWithPaging(pageable)).willReturn(emptyPage);

        // when
        Page<PostDetailReadResult> results = postReadService.getPostList(pageable);

        // then
        assertThat(results).isNotNull();
        assertThat(results.getContent()).isEmpty();
        assertThat(results.getTotalElements()).isEqualTo(0);

        verify(postReadPort).findAllWithPaging(pageable);
    }

    @Test
    @DisplayName("게시글 페이징 조회 - 두 번째 페이지")
    void getPostList_SecondPage() {
        // given
        Pageable pageable = PageRequest.of(1, 1); // 1개씩, 두 번째 페이지
        List<Post> posts = Arrays.asList(samplePost2);
        Page<Post> postPage = new PageImpl<>(posts, pageable, 2);

        given(postReadPort.findAllWithPaging(pageable)).willReturn(postPage);

        // when
        Page<PostDetailReadResult> results = postReadService.getPostList(pageable);

        // then
        assertThat(results).isNotNull();
        assertThat(results.getContent()).hasSize(1);
        assertThat(results.getTotalElements()).isEqualTo(2);
        assertThat(results.getNumber()).isEqualTo(1); // 현재 페이지 번호
        assertThat(results.getContent().get(0).getId()).isEqualTo(2L);
        assertThat(results.getContent().get(0).getTitle()).isEqualTo("두 번째 게시글");

        verify(postReadPort).findAllWithPaging(pageable);
    }
}