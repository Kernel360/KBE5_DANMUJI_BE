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

    @Test
    @DisplayName("질문자는 질문 내용을 수정할 수 있다")
    void updateQuestionContent() {
        // given
        Question question = Question.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("기존 질문 내용")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        question.updateContent("수정된 질문 내용");

        // then
        assertThat(question.getContent()).isEqualTo("수정된 질문 내용");
    }

    @Test
    @DisplayName("질문은 삭제 상태로 마킹될 수 있다")
    void deleteQuestion() {
        // given
        Question question = Question.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("삭제할 질문")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        question.markAsDeleted(); // 삭제는 soft delete로 가정 (필드 추가되어야 함)

        // then
        assertThat(question.isDeleted()).isTrue();
    }

    // todo: 답변(댓글) 달기 -> 얘는 기존의 댓글을 사용할 순 없을거같고.. 새로 엔티티 파서 답변용 도메인을 만들어야하나?
    @Test
    @DisplayName("질문에 답변을 추가할 수 있다")
    void addReplyToQuestion() {
        // given
        Question parent = Question.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("부모 질문")
            .createdAt(LocalDateTime.now())
            .build();

        Question reply = Question.builder()
            .id(2L)
            .postId(1L)
            .authorId(2L)
            .content("답변 내용")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        parent.addReply(reply);

        // then
        assertThat(reply.getParentId()).isEqualTo(parent.getId());
        assertThat(parent.getChildren()).contains(reply);
    }
    
    @Test
    @DisplayName("답변자가 답변을 하면 상태가 ANSWERED로 변경된다")
    void markQuestionAsAnswered() {
        // given
        Question question = Question.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("답변 대기 질문")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        question.markAsAnswered();

        // then
        assertThat(question.getStatus()).isEqualTo(QuestionStatus.ANSWERED);
    }

    @Test
    @DisplayName("질문자가 해결됨을 누르면 상태가 RESOLVED로 변경된다")
    void markQuestionAsResolved() {
        // given
        Question question = Question.builder()
            .id(1L)
            .postId(1L)
            .authorId(1L)
            .content("질문 내용")
            .createdAt(LocalDateTime.now())
            .build();

        // when
        question.markAsResolved();

        // then
        assertThat(question.getStatus()).isEqualTo(QuestionStatus.RESOLVED);
    }

}
