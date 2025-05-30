package com.back2basics.comment.adapter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.adapter.CommentUpdateJpaAdapter;
import com.back2basics.adapter.persistence.comment.utils.CommentRelationHelper;
import com.back2basics.adapter.persistence.comment.utils.CommentUpdateHelper;
import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentUpdateJpaAdapterTest {

    @Mock
    private CommentEntityRepository commentRepository;

    @Mock
    private CommentUpdateHelper commentUpdateHelper;

    @Mock
    private CommentRelationHelper commentRelationHelper;

    @InjectMocks
    private CommentUpdateJpaAdapter commentUpdateJpaAdapter;

    @Test
    @DisplayName("댓글 수정 성공")
    void update_Success() {
        // given
        Comment comment = Comment.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("수정된 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity updatedEntity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("수정된 댓글")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("수정된 댓글")
            .build();

        given(commentUpdateHelper.updateComment(1L, comment)).willReturn(updatedEntity);
        given(commentRepository.save(updatedEntity)).willReturn(savedEntity);

        // when
        commentUpdateJpaAdapter.update(comment);

        // then
        verify(commentUpdateHelper).updateComment(eq(1L), eq(comment));
        verify(commentRelationHelper).assignRelations(
            eq(updatedEntity),
            eq(comment.getPostId()),
            eq(comment.getParentCommentId())
        );
        verify(commentRepository).save(updatedEntity);
    }

    @Test
    @DisplayName("대댓글 수정 성공")
    void update_Reply_Success() {
        // given
        Comment reply = Comment.builder()
            .id(2L)
            .postId(1L)
            .parentCommentId(1L)
            .authorId(1L)
            .content("수정된 대댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity updatedEntity = CommentEntity.builder()
            .id(2L)
            .authorId(1L)
            .content("수정된 대댓글")
            .build();

        CommentEntity savedEntity = CommentEntity.builder()
            .id(2L)
            .authorId(1L)
            .content("수정된 대댓글")
            .build();

        given(commentUpdateHelper.updateComment(2L, reply)).willReturn(updatedEntity);
        given(commentRepository.save(updatedEntity)).willReturn(savedEntity);

        // when
        commentUpdateJpaAdapter.update(reply);

        // then
        verify(commentUpdateHelper).updateComment(eq(2L), eq(reply));
        verify(commentRelationHelper).assignRelations(eq(updatedEntity), eq(1L), eq(1L));
        verify(commentRepository).save(updatedEntity);
    }

    @Test
    @DisplayName("댓글 수정 시 관계 설정이 호출됨")
    void update_RelationAssignmentCalled() {
        // given
        Comment comment = Comment.builder()
            .id(3L)
            .postId(5L)
            .authorId(2L)
            .content("관계 테스트")
            .parentCommentId(null)
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity updatedEntity = CommentEntity.builder()
            .id(3L)
            .authorId(2L)
            .content("관계 테스트")
            .build();

        given(commentUpdateHelper.updateComment(3L, comment)).willReturn(updatedEntity);
        given(commentRepository.save(updatedEntity)).willReturn(updatedEntity);

        // when
        commentUpdateJpaAdapter.update(comment);

        // then
        verify(commentRelationHelper).assignRelations(updatedEntity, 5L, null);
    }

    @Test
    @DisplayName("헬퍼를 통해 댓글 내용이 업데이트됨")
    void update_HelperUpdateCalled() {
        // given
        Comment comment = Comment.builder()
            .id(4L)
            .postId(1L)
            .authorId(1L)
            .content("헬퍼 테스트")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity updatedEntity = CommentEntity.builder()
            .id(4L)
            .authorId(1L)
            .content("헬퍼 테스트")
            .build();

        given(commentUpdateHelper.updateComment(4L, comment)).willReturn(updatedEntity);
        given(commentRepository.save(updatedEntity)).willReturn(updatedEntity);

        // when
        commentUpdateJpaAdapter.update(comment);

        // then
        verify(commentUpdateHelper).updateComment(4L, comment);
    }
}