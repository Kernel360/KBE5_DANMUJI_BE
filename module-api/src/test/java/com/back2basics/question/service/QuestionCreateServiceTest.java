package com.back2basics.question.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.command.QuestionCreateCommand;
import com.back2basics.question.port.out.QuestionCreatePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionCreateService 테스트")
public class QuestionCreateServiceTest {

    @Mock
    private QuestionCreatePort questionCreatePort;

    @InjectMocks
    private QuestionCreateService questionCreateService;

    @Test
    @DisplayName("질문 생성 성공 - 기본 상태는 WAITING")
    void createQuestion_Success() {
        // given
        QuestionCreateCommand command = QuestionCreateCommand.builder()
            .postId(10L)
            .authorId(2L)
            .content("질문 내용입니다.")
            .build();

        given(questionCreatePort.save(any(Question.class))).willReturn(1L);

        // when
        Long resultId = questionCreateService.create(command);

        // then
        assertThat(resultId).isEqualTo(1L);

        ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
        verify(questionCreatePort).save(captor.capture());

        Question saved = captor.getValue();
        assertThat(saved.getPostId()).isEqualTo(10L);
    }
}
