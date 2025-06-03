package com.back2basics.adapter.persistence.question;

import com.back2basics.answer.model.Answer;
import com.back2basics.question.model.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    public QuestionEntity toEntity(Question domain) {

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

    public Question toDomain(QuestionEntity entity) {
        return Question.builder()
            .id(entity.getId())
            .postId(entity.getPostId())
            .authorId(entity.getAuthorId())
            .createdAt(entity.getCreatedAt())
            .content(entity.getContent())
            .build();
    }

    public Question toDomain(QuestionEntity entity, List<Answer> answers) {
        return Question.builder()
            .id(entity.getId())
            .postId(entity.getPostId())
            .authorId(entity.getAuthorId())
            .createdAt(entity.getCreatedAt())
            .content(entity.getContent())
            .answers(answers)
            .build();
    }
}
