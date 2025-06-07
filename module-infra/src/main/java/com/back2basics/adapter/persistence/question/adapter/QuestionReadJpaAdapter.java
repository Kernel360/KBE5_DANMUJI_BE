package com.back2basics.adapter.persistence.question.adapter;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionReadPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionReadJpaAdapter implements QuestionReadPort {

    private final QuestionEntityRepository questionRepository;
    private final QuestionMapper mapper;

    @Override
    public Optional<Question> findById(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));

        return Optional.of(mapper.toDomain(entity));
    }

    @Override
    public Page<Question> findAllByPostId(Long postId, Pageable pageable) {
        return questionRepository.findAllQuestionsByPostId(postId, pageable)
            .map(mapper::toDomain);
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAllNotDeleted(pageable)
            .map(mapper::toDomain);
    }
}

