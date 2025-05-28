package com.back2basics.comment.model;


import static org.assertj.core.api.Assertions.assertThat;

import com.back2basics.service.comment.dto.CommentUpdateCommand;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentModelTest {

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
            .id(1L)
            .authorName("author")
            .content("content")
            .postId(1L)
            .parentCommentId(null)
            .createdAt(LocalDateTime.now())
            .build();

    }

    @Test
    @DisplayName("댓글 수정 메소드")
    void updateMethod() {
        // given
        CommentUpdateCommand commend = new CommentUpdateCommand(
            "requesterName", "new content"
        );

        // when
        comment.update(commend);

        // then
        assertThat(comment.getContent()).isEqualTo("new content");
    }
}
