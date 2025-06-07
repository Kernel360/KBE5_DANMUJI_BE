package com.back2basics.question.service.result;


import com.back2basics.question.model.Question;
import com.back2basics.question.model.QuestionStatus;
import com.back2basics.user.service.result.UserSummaryResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionResult {

    private final Long id;
    private final Long postId;
    private final String authorIp;
    private final UserSummaryResult author;
    private final String content;
    private final QuestionStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    @Builder
    public QuestionResult(Long id, Long postId, String authorIp, UserSummaryResult author,
        String content,
        QuestionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        this.id = id;
        this.postId = postId;
        this.authorIp = authorIp;
        this.author = author;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static QuestionResult toResult(Question question) {
        return QuestionResult.builder()
            .id(question.getId())
            .postId(question.getPostId())
            .authorIp(question.getAuthorIp())
            .author(UserSummaryResult.from(question.getAuthor()))
            .content(question.getContent())
            .status(question.getStatus())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .deletedAt(question.getDeletedAt())
            .build();
    }
}