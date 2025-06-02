package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerUpdateJpaAdapter implements AnswerUpdatePort {

    private final AnswerEntityRepository answerRepository;
    private final AnswerMapper mapper;

    @Override
    public void update(Answer answer) {
        AnswerEntity entity = answerRepository.findById(answer.getId())
            .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));

        entity.update(answer);
        answerRepository.save(entity);
    }
}
