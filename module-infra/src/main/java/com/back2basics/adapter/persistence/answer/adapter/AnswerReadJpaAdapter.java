package com.back2basics.adapter.persistence.answer.adapter;

import static com.back2basics.adapter.persistence.answer.QAnswerEntity.answerEntity;
import static com.back2basics.adapter.persistence.question.QQuestionEntity.questionEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.AnswerReadPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerReadJpaAdapter implements AnswerReadPort {

    private final JPAQueryFactory queryFactory;
    private final AnswerMapper mapper;

    @Override
    public Optional<Answer> findById(Long id) {
        AnswerEntity entity = queryFactory
            .selectFrom(answerEntity)
            .join(answerEntity.author, userEntity).fetchJoin()
            .join(answerEntity.question, questionEntity).fetchJoin()
            .where(
                answerEntity.id.eq(id),
                answerEntity.deletedAt.isNull()
            )
            .fetchOne();

        return Optional.ofNullable(entity).map(mapper::toDomain);
    }

    @Override
    public List<Answer> findAllAnswersByQuestionId(Long questionId) {
        List<AnswerEntity> entities = queryFactory
            .selectFrom(answerEntity)
            .join(answerEntity.author, userEntity).fetchJoin()
            .join(answerEntity.question, questionEntity).fetchJoin()
            .where(
                answerEntity.question.id.eq(questionId),
                answerEntity.deletedAt.isNull()
            )
            .fetch();

        return entities.stream()
            .map(mapper::toDomain)
            .toList();
    }
}
