package com.back2basics.question.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.infra.validator.QuestionValidator;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionDeletePort;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionDeleteService 테스트")
public class QuestionDeleteServiceTest {

    @Mock
    private QuestionValidator questionValidator;

    @Mock
    private QuestionDeletePort questionDeletePort;

    @InjectMocks
    private QuestionDeleteService questionDeleteService;

    @Test
    @DisplayName("작성자가 질문을 소프트 삭제할 수 있다")
    void deleteQuestion_SoftDelete_Success() {
        // given
        Long questionId = 1L;
        Long authorId = 2L;

        Question question = Question.builder()
            .id(questionId)
            .postId(10L)
            .authorId(authorId)
            .content("삭제할 질문입니다.")
            .createdAt(LocalDateTime.now())
            .build();

        given(questionValidator.findById(questionId)).willReturn(question);

        // when
        questionDeleteService.delete(questionId, authorId);

        // then
        verify(questionValidator).validateAuthor(question, authorId);
        verify(questionDeletePort).delete(question.getId());
    }
}