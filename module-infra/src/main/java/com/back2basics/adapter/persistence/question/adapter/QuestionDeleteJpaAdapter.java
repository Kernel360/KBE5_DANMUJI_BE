package com.back2basics.adapter.persistence.question.adapter;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.port.out.QuestionDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionDeleteJpaAdapter implements QuestionDeletePort {

    private final QuestionEntityRepository questionRepository;

    @Override
    public void delete(Long questionId) {
        QuestionEntity entity = questionRepository.findById(questionId)
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));

        entity.markDeleted();
        questionRepository.save(entity);
    }

}
