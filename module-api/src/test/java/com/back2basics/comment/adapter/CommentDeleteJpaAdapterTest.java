package com.back2basics.comment.adapter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.adapter.CommentDeleteJpaAdapter;
import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentDeleteJpaAdapterTest {

    @Mock
    private CommentEntityRepository commentRepository;

    @InjectMocks
    private CommentDeleteJpaAdapter commentDeleteJpaAdapter;

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_Success() {
        // given
        Comment comment = Comment.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("삭제할 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("삭제할 댓글")
            .build();

        given(commentRepository.findById(1L)).willReturn(Optional.of(entity));

        // when
        commentDeleteJpaAdapter.delete(comment);

        // then
        verify(commentRepository).findById(1L);
        verify(commentRepository).delete(entity);
    }

    @Test
    @DisplayName("다른 ID의 댓글 삭제")
    void delete_DifferentId() {
        // given
        Comment comment = Comment.builder()
            .id(3L)
            .postId(2L)
            .authorId(2L)
            .content("삭제할 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(3L)
            .authorId(2L)
            .content("삭제할 댓글")
            .build();

        given(commentRepository.findById(3L)).willReturn(Optional.of(entity));

        // when
        commentDeleteJpaAdapter.delete(comment);

        // then
        verify(commentRepository).findById(3L);
        verify(commentRepository).delete(entity);
    }

    @Test
    @DisplayName("대댓글 삭제 성공")
    void delete_Reply_Success() {
        // given
        Comment reply = Comment.builder()
            .id(5L)
            .postId(1L)
            .parentCommentId(2L)
            .authorId(1L)
            .content("삭제할 대댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(5L)
            .authorId(1L)
            .content("삭제할 대댓글")
            .build();

        given(commentRepository.findById(5L)).willReturn(Optional.of(entity));

        // when
        commentDeleteJpaAdapter.delete(reply);

        // then
        verify(commentRepository).findById(5L);
        verify(commentRepository).delete(entity);
    }

    @Test
    @DisplayName("댓글 ID로 정확한 엔티티 조회")
    void delete_FindByCorrectId() {
        // given
        Long commentId = 42L;
        Comment comment = Comment.builder()
            .id(commentId)
            .postId(1L)
            .authorId(1L)
            .content("ID 테스트")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(commentId)
            .authorId(1L)
            .content("ID 테스트")
            .build();

        given(commentRepository.findById(commentId)).willReturn(Optional.of(entity));

        // when
        commentDeleteJpaAdapter.delete(comment);

        // then
        verify(commentRepository).findById(commentId);
    }
}