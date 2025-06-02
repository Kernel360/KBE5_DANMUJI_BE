package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerReadJpaAdapter implements AnswerReadPort {

    private final AnswerEntityRepository answerEntityRepository;
    private final AnswerMapper mapper;

    @Override
    public List<Answer> findAll() {
        return answerEntityRepository.findAllNotDeletedAnswers().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
