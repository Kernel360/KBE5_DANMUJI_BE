package com.back2basics.adapter.persistence.answer;

import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.answer.model.Answer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
            .parentAnswerId(
                entity.getParentAnswerId() != null ? entity.getParentAnswerId().getId() : null)
            .author(userMapper.toDomain(entity.getAuthor()))
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .children(new ArrayList<>())
            .build();
    }

    public List<Answer> toDomainHierarchy(List<AnswerEntity> entities) {
        Map<Long, Answer> answerMap = new HashMap<>();
        for (AnswerEntity entity : entities) {
            Answer answer = toDomain(entity);
            answerMap.put(answer.getId(), answer); // put:덮어쓰기
        }

        List<Answer> roots = new ArrayList<>();
        for (Answer answer : answerMap.values()) {
            Long parentId = answer.getParentAnswerId();
            if (parentId == null) {
                roots.add(answer);
            } else {
                Answer parent = answerMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(answer);
                }
            }
        }

        return roots;
    }


    public AnswerEntity toEntity(Answer domain) {

        List<AnswerEntity> children = domain.getChildren().stream()
            .map(this::toEntity)
            .collect(Collectors.toCollection(ArrayList::new));

        AnswerEntity entity = AnswerEntity.builder()
            .id(domain.getId())
            .author(userMapper.toEntity(domain.getAuthor()))
            .content(domain.getContent())
            .build();

        entity.assignQuestion(QuestionEntity.builder().id(domain.getQuestionId()).build());

        children.forEach(child -> entity.addChildAnswer(child));
        return entity;
    }

}