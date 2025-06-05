package com.back2basics.answer.model;

import com.back2basics.answer.port.in.command.AnswerUpdateCommand;
import com.back2basics.question.model.Question;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Answer {

    private Long id;
    private Long questionId;
    private Long parentId;
    private User author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Answer(Long id, Long questionId, Long parentId, User author,
        String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.questionId = questionId;
        this.parentId = parentId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(AnswerUpdateCommand command) {
        this.content = command.getContent();
    }

    public void assignQuestionId(Question question) {
        this.questionId = question.getId();
    }


}
