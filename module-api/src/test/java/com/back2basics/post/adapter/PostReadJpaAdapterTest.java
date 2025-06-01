package com.back2basics.post.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.adapter.PostReadJpaAdapter;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostReadJpaAdapter 테스트")
class PostReadJpaAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private CommentEntityRepository commentRepository;

    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostReadJpaAdapter postReadJpaAdapter;

    private PostEntity postEntity1;
    private PostEntity postEntity2;
    private Post post1;
    private Post post2;

    @BeforeEach
    void setUp() {
        postEntity1 = PostEntity.builder()
            .id(1L)
            .authorId(1L)
            .title("첫 번째 게시글")
            .content("첫 번째 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        postEntity2 = PostEntity.builder()
            .id(2L)
            .authorId(2L)
            .title("두 번째 게시글")
            .content("두 번째 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();

        post1 = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("첫 번째 게시글")
            .content("첫 번째 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        post2 = Post.builder()
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
    @DisplayName("ID로 게시글 조회 성공")
    void findById_Success() {
        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.of(postEntity1));
        given(commentRepository.findByPostId(postId)).willReturn(Collections.emptyList());
        given(mapper.toDomain(postEntity1, Collections.emptyList())).willReturn(post1);

        // when
        Optional<Post> result = postReadJpaAdapter.findById(postId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getTitle()).isEqualTo("첫 번째 게시글");

        verify(postRepository).findById(postId);
        verify(commentRepository).findByPostId(postId);
        verify(mapper).toDomain(postEntity1, Collections.emptyList());
    }

    // 없는id로 조회하는거 validator에서 해주는데 여기서 해줄 필요가 없음
    // todo : query dsl 사용한 페이징 테스트 필요. 어케하는지 알아봐야됨
}