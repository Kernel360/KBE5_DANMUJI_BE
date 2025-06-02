package com.back2basics.adapter.persistence.question.adapter;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionCreateJpaAdapter implements QuestionCreatePort {

    private final QuestionEntityRepository questionRepository;

    @Override
    public Long save(Question question) {
        QuestionEntity entity = QuestionMapper.toEntity(question);
        return questionRepository.save(entity).getId();
    }
}