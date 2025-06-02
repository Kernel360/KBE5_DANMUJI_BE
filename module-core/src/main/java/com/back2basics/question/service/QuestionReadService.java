package com.back2basics.question.service;

import com.back2basics.infra.validation.validator.QuestionValidator;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.QuestionReadUseCase;
import com.back2basics.question.port.out.QuestionReadPort;
import com.back2basics.question.service.result.QuestionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionReadService implements QuestionReadUseCase {

    private final QuestionReadPort questionReadPort;
    private final QuestionValidator questionValidator;

    @Override
    public Page<QuestionResult> getQuestionsByPostId(Long postId, Pageable pageable) {
        return questionReadPort.findAllByPostId(postId, pageable)
            .map(QuestionResult::toResult);
    }

    @Override
    public QuestionResult getQuestionById(Long questionId) {
        Question question = questionValidator.findById(questionId);
        return QuestionResult.toResult(question);
    }

    @Override
    public Page<QuestionResult> getAllQuestions(Pageable pageable) {
        return questionReadPort.findAll(pageable)
            .map(QuestionResult::toResult);
    }
}
