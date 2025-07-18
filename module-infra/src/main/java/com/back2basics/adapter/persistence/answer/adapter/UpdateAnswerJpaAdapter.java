package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.UpdateAnswerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAnswerJpaAdapter implements UpdateAnswerPort {

    private final AnswerMapper mapper;
    private final AnswerEntityRepository answerRepository;

    @Override
    public void update(Answer answer) {
        answerRepository.save(mapper.toEntity(answer));
    }
}
