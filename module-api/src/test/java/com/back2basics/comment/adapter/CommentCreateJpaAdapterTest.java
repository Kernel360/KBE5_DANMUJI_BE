package com.back2basics.comment.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.adapter.persistence.comment.adapter.CommentCreateJpaAdapter;
import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentCreateJpaAdapterTest {

    @Mock
    private CommentEntityRepository commentRepository;

    @Mock
    private CommentMapper mapper;

    @Mock
    private CommentRelationHelper commentRelationHelper;

    @InjectMocks
    private CommentCreateJpaAdapter commentCreateJpaAdapter;

    @Test
    @DisplayName("댓글 생성 성공")
    void save_Success() {
        // given
        Comment comment = Comment.builder()
            .postId(1L)
            .authorId(1L)
            .content("새 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(null)
            .authorId(1L)
            .content("새 댓글")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("새 댓글")
            .build();

        given(mapper.fromDomain(comment)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        Long result = commentCreateJpaAdapter.save(comment);

        // then
        assertThat(result).isEqualTo(1L);
        verify(mapper).fromDomain(comment);
        verify(commentRelationHelper).assignRelations(
            eq(entity),
            eq(comment.getPostId()),
            eq(comment.getParentCommentId())
        );
        verify(commentRepository).save(entity);
    }

    @Test
    @DisplayName("대댓글 생성 성공")
    void save_Reply_Success() {
        // given
        Comment reply = Comment.builder()
            .postId(1L)
            .parentCommentId(2L)
            .authorId(1L)
            .content("대댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(null)
            .authorId(1L)
            .content("대댓글")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(3L)
            .authorId(1L)
            .content("대댓글")
            .build();

        given(mapper.fromDomain(reply)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        Long result = commentCreateJpaAdapter.save(reply);

        // then
        assertThat(result).isEqualTo(3L);
        verify(mapper).fromDomain(reply);
        verify(commentRelationHelper).assignRelations(eq(entity), eq(1L), eq(2L));
        verify(commentRepository).save(entity);
    }

    @Test
    @DisplayName("댓글 생성 시 관계 설정이 호출됨")
    void save_RelationAssignmentCalled() {
        // given
        Comment comment = Comment.builder()
            .postId(5L)
            .authorId(2L)
            .content("테스트 댓글")
            .parentCommentId(null)
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .authorId(2L)
            .content("테스트 댓글")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(10L)
            .authorId(2L)
            .content("테스트 댓글")
            .build();

        given(mapper.fromDomain(comment)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        commentCreateJpaAdapter.save(comment);

        // then
        verify(commentRelationHelper).assignRelations(entity, 5L, null);
    }

    @Test
    @DisplayName("매퍼를 통해 도메인에서 엔티티로 변환됨")
    void save_MapperFromDomainCalled() {
        // given
        Comment comment = Comment.builder()
            .postId(1L)
            .authorId(1L)
            .content("매퍼 테스트")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .authorId(1L)
            .content("매퍼 테스트")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("매퍼 테스트")
            .build();

        given(mapper.fromDomain(comment)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        commentCreateJpaAdapter.save(comment);

        // then
        verify(mapper).fromDomain(comment);
    }

    @Test
    @DisplayName("저장된 엔티티의 ID가 반환됨")
    void save_ReturnsEntityId() {
        // given
        Comment comment = Comment.builder()
            .postId(1L)
            .authorId(1L)
            .content("ID 반환 테스트")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .authorId(1L)
            .content("ID 반환 테스트")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(42L)
            .authorId(1L)
            .content("ID 반환 테스트")
            .build();

        given(mapper.fromDomain(comment)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        Long result = commentCreateJpaAdapter.save(comment);

        // then
        assertThat(result).isEqualTo(42L);
    }
}