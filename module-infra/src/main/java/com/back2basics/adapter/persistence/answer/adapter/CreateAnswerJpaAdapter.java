package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.CreateAnswerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAnswerJpaAdapter implements CreateAnswerPort {

    private final AnswerEntityRepository answerRepository;
    private final AnswerMapper mapper;

    @Override
    public Long save(Answer answer) {
        AnswerEntity entity = mapper.toEntity(answer);

        return answerRepository.save(entity).getId();
    }

}
