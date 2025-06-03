package com.back2basics.adapter.persistence.question.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionReadPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionReadJpaAdapter implements QuestionReadPort {

    private final QuestionEntityRepository questionRepository;
    private final AnswerEntityRepository answerRepository;
    private final QuestionMapper mapper;
    private final AnswerMapper answerMapper;

    @Override
    public Optional<Question> findById(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));

        List<AnswerEntity> answerEntities = answerRepository.findByQuestionId(entity.getId());
        List<Answer> answers = answerEntities.stream()
            .map(answerMapper::toDomain)
            .collect(Collectors.toList());

        return Optional.of(mapper.toDomain(entity, answers));
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

