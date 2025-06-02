package com.back2basics.comment.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.adapter.persistence.comment.adapter.CommentReadJpaAdapter;
import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentReadJpaAdapterTest {

    @Mock
    private CommentEntityRepository commentEntityRepository;

    @Mock
    private CommentMapper mapper;

    @InjectMocks
    private CommentReadJpaAdapter commentReadJpaAdapter;

    private CommentEntity entity;
    private Comment comment;
    private Long commentId;

    @BeforeEach
    void setUp() {
        commentId = 1L;

        entity = CommentEntity.builder()
            .id(commentId)
            .authorId(1L)
            .content("테스트 댓글")
            .build();

        comment = Comment.builder()
            .id(commentId)
            .postId(1L)
            .authorId(1L)
            .content("테스트 댓글")
            .createdAt(LocalDateTime.now())
            .build();
    }


    @Test
    @DisplayName("댓글 조회 성공")
    void findById_Success() {
        // given
        Long commentId = 1L;
        given(commentEntityRepository.findById(commentId)).willReturn(Optional.of(entity));
        given(mapper.toDomain(entity)).willReturn(comment);

        // when
        Optional<Comment> result = commentReadJpaAdapter.findById(commentId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(comment);
        assertThat(result.get().getId()).isEqualTo(commentId);
        verify(commentEntityRepository).findById(commentId);
        verify(mapper).toDomain(entity);
    }

    @Test
    @DisplayName("존재하지 않는 댓글 조회 시 비어있는 Optional 결과로 반환")
    void findById_NotFound_ReturnsEmpty() {
        // given
        Long commentId = 999L;
        given(commentEntityRepository.findById(commentId)).willReturn(Optional.empty());

        // when
        Optional<Comment> result = commentReadJpaAdapter.findById(commentId);

        // then
        assertThat(result).isEmpty();
        verify(commentEntityRepository).findById(commentId);
    }

    @Test
    @DisplayName("다른 ID로 댓글 조회")
    void findById_DifferentId() {
        // given
        Long commentId = 42L;
        CommentEntity entity = CommentEntity.builder()
            .id(commentId)
            .authorId(2L)
            .content("다른 댓글")
            .build();

        Comment comment = Comment.builder()
            .id(commentId)
            .postId(2L)
            .authorId(2L)
            .content("다른 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        given(commentEntityRepository.findById(commentId)).willReturn(Optional.of(entity));
        given(mapper.toDomain(entity)).willReturn(comment);

        // when
        Optional<Comment> result = commentReadJpaAdapter.findById(commentId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(commentId);
        assertThat(result.get().getAuthorId()).isEqualTo(2L);
        verify(commentEntityRepository).findById(commentId);
    }

    @Test
    @DisplayName("매퍼를 통해 엔티티에서 도메인으로 변환됨")
    void findById_MapperToDomainCalled() {
        // given
        Long commentId = 1L;
        given(commentEntityRepository.findById(commentId)).willReturn(Optional.of(entity));
        given(mapper.toDomain(entity)).willReturn(comment);

        // when
        commentReadJpaAdapter.findById(commentId);

        // then
        verify(mapper).toDomain(entity);
    }

    @Test
    @DisplayName("대댓글 조회 성공")
    void findById_Reply_Success() {
        // given
        Long replyId = 5L;
        CommentEntity entity = CommentEntity.builder()
            .id(replyId)
            .authorId(1L)
            .content("대댓글")
            .build();

        Comment reply = Comment.builder()
            .id(replyId)
            .postId(1L)
            .parentCommentId(2L)
            .authorId(1L)
            .content("대댓글")
            .createdAt(LocalDateTime.now())
            .build();

        given(commentEntityRepository.findById(replyId)).willReturn(Optional.of(entity));
        given(mapper.toDomain(entity)).willReturn(reply);

        // when
        Optional<Comment> result = commentReadJpaAdapter.findById(replyId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getParentCommentId()).isEqualTo(2L);
        verify(commentEntityRepository).findById(replyId);
        verify(mapper).toDomain(entity);
    }
}