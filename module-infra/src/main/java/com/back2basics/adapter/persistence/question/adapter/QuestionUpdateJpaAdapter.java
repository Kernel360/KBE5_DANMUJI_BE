package com.back2basics.adapter.persistence.question.adapter;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionUpdateJpaAdapter implements QuestionUpdatePort {

    private final QuestionEntityRepository questionRepository;

    @Override
    public void update(Question question) {
        QuestionEntity entity = questionRepository.findById(question.getId())
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));

        entity.update(question);
        questionRepository.save(entity);
    }
}