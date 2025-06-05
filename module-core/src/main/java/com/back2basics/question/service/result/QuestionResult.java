package com.back2basics.question.service.result;


import com.back2basics.answer.service.result.AnswerReadResult;
import com.back2basics.question.model.Question;
import com.back2basics.question.model.QuestionStatus;
import com.back2basics.user.service.result.UserInfoResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionResult {

    private final Long id;
    private final Long postId;
    private final UserInfoResult author;
    private final String content;
    private final QuestionStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final List<AnswerReadResult> answers;

    @Builder
    public QuestionResult(Long id, Long postId, UserInfoResult author, String content,
        QuestionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, List<AnswerReadResult> answers) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.answers = answers;
    }

    public static QuestionResult toResult(Question question) {
        List<AnswerReadResult> answerResults = question.getAnswers().stream()
            .map(AnswerReadResult::toResult)
            .collect(Collectors.toList());

        return QuestionResult.builder()
            .id(question.getId())
            .postId(question.getPostId())
            .author(UserInfoResult.toResult(question.getAuthor()))
            .content(question.getContent())
            .status(question.getStatus())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .deletedAt(question.getDeletedAt())
            .answers(answerResults)
            .build();
    }
}