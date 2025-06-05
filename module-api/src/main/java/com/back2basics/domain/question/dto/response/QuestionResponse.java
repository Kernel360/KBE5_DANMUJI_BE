package com.back2basics.domain.question.dto.response;

import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import com.back2basics.question.model.QuestionStatus;
import com.back2basics.question.service.result.QuestionResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {

    private Long id;
    private Long postId;
    private UserSummaryResponse author;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static QuestionResponse toResponse(QuestionResult result) {
        return QuestionResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .author(UserSummaryResponse.from(result.getAuthor()))
            .content(result.getContent())
            .status(result.getStatus())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .deletedAt(result.getDeletedAt())
            .build();
    }
}
