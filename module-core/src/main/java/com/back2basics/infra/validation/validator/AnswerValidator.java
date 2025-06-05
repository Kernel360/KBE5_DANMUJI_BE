package com.back2basics.infra.validation.validator;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.AnswerReadPort;
import com.back2basics.infra.exception.answer.AnswerErrorCode;
import com.back2basics.infra.exception.answer.AnswerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerValidator {

    private final AnswerReadPort answerRepository;

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id)
            .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));
    }

    public void isAuthor(Answer answer, Long requesterId) {
        if (!answer.getAuthor().getId().equals(requesterId)) {
            throw new AnswerException(AnswerErrorCode.INVALID_ANSWER_AUTHOR);
        }
    }

    // 부모 댓글이 존재할 경우, 그 부모 답변의 questionId가 현재 답변이 달릴 answerId와 일치하는지 검증하는 메소드 이름 추천받습니다.
    public void validateParentPost(Long parentAnswerId, Long targetQuestionId) {
        Answer parentAnswer = findAnswerById(parentAnswerId);
        if (!parentAnswer.getQuestionId().equals(targetQuestionId)) {
            throw new AnswerException(AnswerErrorCode.INVALID_ANSWER_PARENT_QUESTION_ID);
        }
    }
}
