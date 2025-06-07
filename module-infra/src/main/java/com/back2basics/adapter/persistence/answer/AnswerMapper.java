package com.back2basics.adapter.persistence.answer;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.answer.model.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    private final UserMapper userMapper;

    public Answer toDomain(AnswerEntity entity) {
        return Answer.builder()
            .id(entity.getId())
            .questionId(entity.getQuestion().getId())
            .parentId(entity.getParentId())
            .authorIp(entity.getAuthorIp())
            .author(userMapper.toDomain(entity.getAuthor()))
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }


    public AnswerEntity toEntity(Answer domain) {
        AnswerEntity entity = AnswerEntity.builder()
            .id(domain.getId())
            .authorIp(domain.getAuthorIp())
            .author(userMapper.toEntity(domain.getAuthor()))
            .parentId(domain.getParentId())
            .content(domain.getContent())
            .build();

        entity.assignQuestion(QuestionEntity.builder().id(domain.getQuestionId()).build());

        return entity;
    }

}