package com.back2basics.adapter.persistence.question.adapter;

import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionUpdateJpaAdapter implements QuestionUpdatePort {

    private final QuestionMapper mapper;
    private final QuestionEntityRepository questionRepository;

    @Override
    public void update(Question question) {
        questionRepository.save(mapper.toEntity(question));
    }
}