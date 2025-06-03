package com.back2basics.comment.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.adapter.persistence.comment.adapter.CommentCreateJpaAdapter;
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
class AnswerCreateJpaAdapterTest {

    @Mock
    private CommentEntityRepository commentRepository;

    @Mock
    private CommentMapper mapper;

    @InjectMocks
    private CommentCreateJpaAdapter commentCreateJpaAdapter;

    private Comment comment;
    private CommentEntity existingEntity;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("수정된 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        existingEntity = CommentEntity.builder()
            .id(1L)
            .authorId(1L)
            .content("기존 댓글")
            .build();
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void save_Success() {
        // given
        given(mapper.toEntity(comment)).willReturn(existingEntity);
        given(commentRepository.save(existingEntity)).willReturn(existingEntity);

        // when
        Long result = commentCreateJpaAdapter.save(comment);

        // then
        assertThat(result).isEqualTo(1L);
        verify(mapper).toEntity(comment);
        verify(commentRepository).save(existingEntity);
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

        Comment parentReply = Comment.builder()
            .postId(1L)
            .parentCommentId(2L)
            .authorId(1L)
            .content("부모 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentEntity parentEntity = CommentEntity.builder()
            .id(2L)
            .authorId(1L)
            .content("부모 댓글")
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

        given(commentRepository.findById(2L)).willReturn(Optional.of(parentEntity));
        given(mapper.toEntity(parentReply)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        Long result = commentCreateJpaAdapter.save(parentReply);

        // then
        assertThat(result).isEqualTo(3L);
        verify(mapper).toEntity(parentReply);
        verify(commentRepository).save(entity);
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

        given(mapper.toEntity(comment)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        commentCreateJpaAdapter.save(comment);

        // then
        verify(mapper).toEntity(comment);
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

        given(mapper.toEntity(comment)).willReturn(entity);
        given(commentRepository.save(entity)).willReturn(savedEntity);

        // when
        Long result = commentCreateJpaAdapter.save(comment);

        // then
        assertThat(result).isEqualTo(42L);
    }
}