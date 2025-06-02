package com.back2basics.question.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.question.exception.QuestionException;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.command.QuestionUpdateCommand;
import com.back2basics.question.port.out.QuestionUpdatePort;
import com.back2basics.question.validator.QuestionValidator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionUpdateService 테스트")
public class QuestionUpdateServiceTest {

    @Mock
    private QuestionValidator questionValidator;

    @Mock
    private QuestionUpdatePort questionUpdatePort;

    @InjectMocks
    private QuestionUpdateService questionUpdateService;

    @Test
    @DisplayName("질문자는 질문 내용을 수정할 수 있다")
    void updateQuestion_Success() {
        // given
        Long questionId = 1L;
        Long authorId = 2L;

        Question question = Question.builder()
            .id(questionId)
            .postId(10L)
            .authorId(authorId)
            .content("이전 질문 내용")
            .createdAt(LocalDateTime.now())
            .build();

        given(questionValidator.findById(questionId)).willReturn(question);

        QuestionUpdateCommand command = QuestionUpdateCommand.builder()
            .requesterId(authorId)
            .content("수정된 질문 내용")
            .build();

        // when
        questionUpdateService.update(questionId, command);

        // then
        verify(questionValidator).validateAuthor(question, authorId);
        verify(questionUpdatePort).update(question);
        assertThat(question.getContent()).isEqualTo("수정된 질문 내용");
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 수정하려 하면 예외 발생")
    void updateQuestion_InvalidAuthor() {
        // given
        Long questionId = 1L;
        Long authorId = 2L;
        Long otherId = 3L;

        Question question = Question.builder()
            .id(questionId)
            .postId(10L)
            .authorId(authorId)
            .content("이전 질문 내용")
            .createdAt(LocalDateTime.now())
            .build();

        given(questionValidator.findById(questionId)).willReturn(question);

        QuestionUpdateCommand command = QuestionUpdateCommand.builder()
            .requesterId(otherId)
            .content("불법 수정")
            .build();

        // when & then
        assertThatThrownBy(() -> questionUpdateService.update(questionId, command))
            .isInstanceOf(QuestionException.class)
            .hasMessageContaining(QuestionErrorCode.INVALID_QUESTION_AUTHOR.getMessage());
    }
}