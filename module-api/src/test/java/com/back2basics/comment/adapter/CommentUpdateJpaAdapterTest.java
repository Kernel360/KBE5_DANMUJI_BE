package com.back2basics.comment.adapter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.adapter.CommentUpdateJpaAdapter;
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
class CommentUpdateJpaAdapterTest {

    @Mock
    private CommentEntityRepository commentRepository;

    @InjectMocks
    private CommentUpdateJpaAdapter commentUpdateJpaAdapter;

    private Comment comment;
    private CommentEntity entity;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("수정된 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        entity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("수정된 댓글")
            .build();
    }


    @Test
    @DisplayName("댓글 수정 성공")
    void update_Success() {
        // given
        given(commentRepository.findById(1L)).willReturn(Optional.of(entity));
        given(commentRepository.save(entity)).willReturn(entity);

        // when
        commentUpdateJpaAdapter.update(comment);

        // then
        assertThat(entity.getContent()).isEqualTo("수정된 댓글");
        verify(commentRepository).findById(1L);
        verify(commentRepository).save(entity);
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

        given(commentRepository.findById(2L)).willReturn(Optional.of(updatedEntity));
        given(commentRepository.save(updatedEntity)).willReturn(savedEntity);

        // when
        commentUpdateJpaAdapter.update(reply);

        // then
        assertThat(savedEntity.getContent()).isEqualTo("수정된 대댓글");
        verify(commentRepository).findById(2L);
        verify(commentRepository).save(updatedEntity);
    }

}