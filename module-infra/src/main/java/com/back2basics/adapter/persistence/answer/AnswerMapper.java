package com.back2basics.adapter.persistence.answer;

import com.back2basics.answer.model.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    public Answer toDomain(AnswerEntity entity) {
        return Answer.builder()
            .id(entity.getId())
            .inquiryId(entity.getInquiryId())
            .authorId(entity.getAuthorId())
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .build();
    }


    public AnswerEntity toEntity(Answer domain) {
        AnswerEntity entity = AnswerEntity.builder()
            .inquiryId(domain.getInquiryId())
            .authorId(domain.getAuthorId())
            .content(domain.getContent())
            .build();

        return entity;
    }

}