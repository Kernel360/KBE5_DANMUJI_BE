package com.back2basics.post.adapter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.adapter.PostCreateJpaAdapter;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostCreateJpaAdapter 테스트")
class PostCreateJpaAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostCreateJpaAdapter postCreateJpaAdapter;

    @Test
    @DisplayName("게시글 저장 성공")
    void save_Success() {
        // given
        Post post = Post.builder()
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        PostEntity postEntity = PostEntity.builder()
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        given(mapper.toEntity(post)).willReturn(postEntity);

        // when
        postCreateJpaAdapter.save(post);

        // then
        verify(mapper).toEntity(post);
        verify(postRepository).save(postEntity);
    }

    @Test
    @DisplayName("삭제된 게시글 저장")
    void save_DeletedPost() {
        // given
        Post deletedPost = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("삭제된 게시글")
            .content("삭제된 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();
        deletedPost.markDeleted();

        PostEntity deletedEntity = PostEntity.builder()
            .id(1L)
            .authorId(1L)
            .title("삭제된 게시글")
            .content("삭제된 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();
        deletedEntity.markDeleted();

        given(mapper.toEntity(deletedPost)).willReturn(deletedEntity);

        // when
        postCreateJpaAdapter.save(deletedPost);

        // then
        verify(mapper).toEntity(deletedPost);
        verify(postRepository).save(deletedEntity);
    }
}