package com.back2basics.question.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        QuestionCreateResult result = questionCreateService.create(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo("질문 내용입니다.");
        assertThat(result.getAuthorId()).isEqualTo(2L);
        assertThat(result.getPostId()).isEqualTo(10L);
        assertThat(result.getStatus()).isEqualTo(QuestionStatus.WAITING);

        ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
        verify(questionCreatePort).save(captor.capture());

        Question saved = captor.getValue();
        assertThat(saved.getPostId()).isEqualTo(10L);
        assertThat(saved.getAuthorId()).isEqualTo(2L);
        assertThat(saved.getContent()).isEqualTo("질문 내용입니다.");
        assertThat(saved.getStatus()).isEqualTo(QuestionStatus.WAITING);
        assertThat(saved.getCreatedAt()).isNotNull();
    }
}
