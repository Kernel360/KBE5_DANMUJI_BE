package com.back2basics.question.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.infra.validation.validator.QuestionValidator;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionReadPort;
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
@DisplayName("QuestionValidator 테스트")
class QuestionValidatorTest {

    @Mock
    private QuestionReadPort questionReadPort;

    @InjectMocks
    private QuestionValidator questionValidator;

    private final Long questionId = 1L;
    private final Long authorId = 1L;
    private final Long anotherId = 2L;
    private Question sampleQuestion;

    @BeforeEach
    void setUp() {
        sampleQuestion = Question.builder()
            .id(questionId)
            .postId(10L)
            .authorId(authorId)
            .content("테스트 질문입니다.")
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("없는 ID로 조회 시 예외 발생")
    void QuestionNotFoundExceptionThrow() {
        // given
        given(questionReadPort.findById(questionId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> questionValidator.findById(questionId))
            .isInstanceOf(QuestionException.class)
            .hasMessage(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("질문이 존재하면 정상 반환")
    void findById_Success() {
        // given
        given(questionReadPort.findById(questionId)).willReturn(Optional.of(sampleQuestion));

        // when
        Question found = questionValidator.findById(questionId);

        // then
        assertThat(found).isEqualTo(sampleQuestion);
    }

    @Test
    @DisplayName("작성자 불일치 시 예외 발생")
    void InvalidAuthorExceptionThrow() {
        // when & then
        assertThatThrownBy(() -> questionValidator.validateAuthor(sampleQuestion, anotherId))
            .isInstanceOf(QuestionException.class)
            .hasMessage(QuestionErrorCode.INVALID_QUESTION_AUTHOR.getMessage());
    }

    @Test
    @DisplayName("작성자 일치 시 정상 동작")
    void validateAuthor_Success() {
        // when & then
        questionValidator.validateAuthor(sampleQuestion, authorId);
    }
}