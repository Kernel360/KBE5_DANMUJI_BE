package com.back2basics.question;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.question.model.Question;
import com.back2basics.question.model.QuestionStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuestionModelTest {

    // todo : 수정, 삭제
    // todo : 답변(댓글) 달기
    // todo : 질문 상태(대기,답변완료,해결)

    @Test
    @DisplayName("질문 생성 시 모든 필드가 올바르게 설정된다")
    void createQueestion_Success() {
        // given
        Long authorId = 1L;
        String content = "테스트 질문입니다.";
        LocalDateTime now = LocalDateTime.now();

        // when
        Question question = Question.builder()
            .id(1L)
            .postId(1L)
            .authorId(authorId)
            .content(content)
            .createdAt(now)
            .build();

        // then
        assertThat(question.getId()).isEqualTo(1L);
        assertThat(question.getPostId()).isEqualTo(1L);
        assertThat(question.getAuthorId()).isEqualTo(authorId);
        assertThat(question.getContent()).isEqualTo(content);
        assertThat(question.getStatus()).isEqualTo(QuestionStatus.WAITING);
        assertThat(question.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("질문 생성 시 게시글과 연결된다")
    void createQuestion_LinkedToPost() {
        // given
        Post post = Post.builder()
            .id(10L)
            .authorId(1L)
            .title("게시글 제목")
            .content("게시글 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        // when
        Question question = Question.builder()
            .id(1L)
            .postId(post.getId())
            .authorId(2L)
            .content("게시글에 대한 질문입니다.")
            .createdAt(LocalDateTime.now())
            .build();

        // then
        assertThat(question.getPostId()).isEqualTo(post.getId());
        assertThat(question.getContent()).isEqualTo("게시글에 대한 질문입니다.");
        assertThat(question.getAuthorId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("질문 생성 시 기본 상태는 WAITING이다")
    void createQuestion_DefaultStatusWaiting() {
        // given
        Question question = Question.builder()
            .id(1L)
            .postId(10L)
            .authorId(2L)
            .content("질문입니다.")
            .createdAt(LocalDateTime.now())
            .build();

        // then
        assertThat(question.getStatus()).isEqualTo(QuestionStatus.WAITING);
    }
}
