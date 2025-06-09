package com.back2basics.adapter.persistence.question.adapter;

import static com.back2basics.adapter.persistence.question.QQuestionEntity.questionEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionReadPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionReadJpaAdapter implements QuestionReadPort {

    private final JPAQueryFactory queryFactory;
    private final QuestionEntityRepository questionRepository;
    private final QuestionMapper mapper;

    @Override
    public Optional<Question> findById(Long id) {
        QuestionEntity entity = queryFactory
            .selectFrom(questionEntity)
            .join(questionEntity.author, userEntity).fetchJoin()
            .where(questionEntity.id.eq(id))
            .fetchOne();

        if (entity == null) {
            throw new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND);
        }

        return Optional.of(mapper.toDomain(entity));
    }

    @Override
    public Page<Question> findAllByPostId(Long postId, Pageable pageable) {
        // id로 페이징
        List<Long> ids = queryFactory
            .select(questionEntity.id)
            .from(questionEntity)
            .where(questionEntity.postId.eq(postId))
            .orderBy(questionEntity.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        if (ids.isEmpty()) { // 하나도 없을 경우
            return Page.empty(pageable);
        }

        // id로 fetch join
        List<Question> questions = queryFactory
            .selectFrom(questionEntity)
            .join(questionEntity.author, userEntity).fetchJoin()
            .where(questionEntity.id.in(ids))
            .orderBy(questionEntity.createdAt.desc())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory
            .select(questionEntity.count())
            .from(questionEntity)
            .where(questionEntity.postId.eq(postId))
            .fetchOne();

        // Unboxing of 'total' may produce 'NullPointerException'
        // total이 null일 수 있으며 그럴 경우 카운트쿼리가 결과를 반환하지 못해서
        // NPE 날 수 있다는 경고에 의한 조건 추가
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(questions, pageable, total);
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {
        // id로 페이징
        List<Long> ids = queryFactory
            .select(questionEntity.id)
            .from(questionEntity)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(questionEntity.createdAt.desc())
            .fetch();

        if (ids.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        // id로 fetch join
        List<Question> questions = queryFactory
            .selectFrom(questionEntity)
            .join(questionEntity.author, userEntity).fetchJoin()
            .where(questionEntity.id.in(ids))
            .orderBy(questionEntity.createdAt.desc())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory
            .select(questionEntity.count())
            .from(questionEntity)
            .fetchOne();
        
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(questions, pageable, total);
    }
}

