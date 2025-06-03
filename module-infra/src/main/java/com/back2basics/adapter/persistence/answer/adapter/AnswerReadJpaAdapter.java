package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.AnswerReadPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerReadJpaAdapter implements AnswerReadPort {

    private final AnswerEntityRepository answerRepository;
    private final AnswerMapper mapper;

    @Override
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAllAnswersNotDeleted().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
