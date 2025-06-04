package com.back2basics.question.model;

import com.back2basics.answer.model.Answer;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Question {

    private Long id;
    private Long postId;
    private User author;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;
    private List<Answer> answers;
    private boolean isDeleted;

    @Builder
    public Question(Long id, Long postId, User author, String content, LocalDateTime createdAt,
        List<Answer> answers,
        boolean isDeleted) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status != null ? status : QuestionStatus.WAITING;
        this.answers = answers != null ? new ArrayList<>(answers) : new ArrayList<>();
        this.isDeleted = false;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void markAsAnswered() {
        this.status = QuestionStatus.ANSWERED;
    }

    public void markAsResolved() {
        this.status = QuestionStatus.RESOLVED;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.assignQuestionId(this);
    }
}
