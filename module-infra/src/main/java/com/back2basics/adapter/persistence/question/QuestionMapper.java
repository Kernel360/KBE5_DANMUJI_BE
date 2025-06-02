package com.back2basics.adapter.persistence.question;

import com.back2basics.question.model.Question;

public class QuestionMapper {

    public static QuestionEntity toEntity(Question domain) {
        return QuestionEntity.builder()
            .id(domain.getId())
            .postId(domain.getPostId())
            .authorId(domain.getAuthorId())
            .content(domain.getContent())
            .status(domain.getStatus())
            .deleted(domain.isDeleted())
            .createdAt(domain.getCreatedAt())
            .build();
    }

    public static Question toDomain(QuestionEntity entity) {
        return Question.builder()
            .id(entity.getId())
            .postId(entity.getPostId())
            .authorId(entity.getAuthorId())
            .content(entity.getContent())
            .status(entity.getStatus())
            .deleted(entity.isDeleted())
            .createdAt(entity.getCreatedAt())
            .build();
    }
}
