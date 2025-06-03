package com.back2basics.answer.model;

import com.back2basics.answer.port.in.command.AnswerUpdateCommand;
import com.back2basics.question.model.Question;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Answer {

    private Long id;
    private Long questionId;
    private Long parentAnswerId;
    private final Long authorId;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Answer> children;

    @Builder
    public Answer(Long id, Long questionId, Long parentAnswerId, Long authorId,
        String content,
        LocalDateTime createdAt, LocalDateTime updatedAt, List<Answer> children) {
        this.id = id;
        this.questionId = questionId;
        this.parentAnswerId = parentAnswerId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.children = children != null ? children : new ArrayList<>();
    }

    public void update(AnswerUpdateCommand command) {
        this.content = command.getContent();

    }

    public void assignQuestionId(Question question) {
        this.questionId = question.getId();
    }

    public void assignParent(Answer parent) {
        this.parentAnswerId = parent.getId();
    }

    public void addChild(Answer child) {
        children.add(child);
        child.assignParent(this);
    }

}
