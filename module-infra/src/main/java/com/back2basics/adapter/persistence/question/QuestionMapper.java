package com.back2basics.adapter.persistence.question;

import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.question.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final UserMapper userMapper;

    public QuestionEntity toEntity(Question domain) {

        QuestionEntity entity = QuestionEntity.builder()
            .id(domain.getId())
            .postId(domain.getPostId())
            .author(userMapper.toEntity(domain.getAuthor()))
            .content(domain.getContent())
            .status(domain.getStatus())
            .isDeleted(domain.isDeleted())
            .build();

        if (domain.isDeleted()) {
            entity.markDeleted();
        }

        return entity;
    }

    public Question toDomain(QuestionEntity entity) {
        return Question.builder()
            .id(entity.getId())
            .postId(entity.getPostId())
            .author(userMapper.toDomain(entity.getAuthor()))
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .deletedAt(entity.getDeletedAt())
            .status(entity.getStatus())
            .content(entity.getContent())
            .build();
    }

}
