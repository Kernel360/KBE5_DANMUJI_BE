package com.back2basics.adapter.persistence.question.adapter;


import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.model.QuestionStatus;
import com.back2basics.question.port.out.QuestionStatusUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionStatusUpdateJpaAdapter implements QuestionStatusUpdatePort {

    private final QuestionEntityRepository questionRepository;
    private final QuestionMapper mapper;

    @Override
    public void markAsAnswered(Long questionId) {
        QuestionEntity entity = questionRepository.findById(questionId)
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));

        entity.updateStatus(QuestionStatus.ANSWERED);
        questionRepository.save(entity);
    }

    @Override
    public void markAsResolved(Long questionId) {
        QuestionEntity entity = questionRepository.findById(questionId)
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));

        entity.updateStatus(QuestionStatus.RESOLVED);
        questionRepository.save(entity);
    }
}

