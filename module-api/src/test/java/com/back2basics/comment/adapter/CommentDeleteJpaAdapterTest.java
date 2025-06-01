package com.back2basics.comment.adapter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.adapter.persistence.comment.adapter.CommentDeleteJpaAdapter;
import com.back2basics.comment.model.Comment;
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

    @Mock
    private CommentMapper mapper;

    @Mock
    private CommentDeleteHelper commentDeleteHelper;

    @InjectMocks
    private CommentDeleteJpaAdapter commentDeleteJpaAdapter;

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_Success() {
        // given
        Long commentId = 1L;
        Comment comment = Comment.builder()
            .id(commentId)
            .postId(1L)
            .authorId(1L)
            .content("테스트 댓글")
            .build();

        CommentEntity entity = CommentEntity.builder()
            .id(commentId)
            .authorId(1L)
            .content("테스트 댓글")
            .build();

        given(mapper.fromDomain(comment)).willReturn(entity);

        // when
        commentDeleteJpaAdapter.delete(comment);

        // then
        verify(commentDeleteHelper).deleteCommentWithOrphanKeep(commentId);
        verify(commentRepository).delete(entity);
    }
}