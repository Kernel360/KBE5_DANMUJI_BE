package com.back2basics.adapter.persistence.question;

import com.back2basics.question.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    public static QuestionEntity toEntity(Question domain) {

        QuestionEntity entity = QuestionEntity.builder()
            .id(domain.getId())
            .postId(domain.getPostId())
            .authorId(domain.getAuthorId())
            .content(domain.getContent())
            .status(domain.getStatus())
            .isDeleted(domain.isDeleted())
            .build();

        if (domain.isDeleted()) {
            entity.markDeleted();
        }

        return entity;
    }

    public static Question toDomain(QuestionEntity entity) {
        return Question.builder()
            .id(entity.getId())
            .postId(entity.getPostId())
            .authorId(entity.getAuthorId())
            .createdAt(entity.getCreatedAt())
            .content(entity.getContent())
            .build();
    }
}
