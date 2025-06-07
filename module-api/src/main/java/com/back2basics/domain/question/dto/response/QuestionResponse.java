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

    private final Long id;
    private final Long postId;
    private final String authorIp;
    private final UserSummaryResponse author;
    private final String content;
    private final QuestionStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static QuestionResponse toResponse(QuestionResult result) {
        return QuestionResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .authorIp(result.getAuthorIp())
            .author(UserSummaryResponse.from(result.getAuthor()))
            .content(result.getContent())
            .status(result.getStatus())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .deletedAt(result.getDeletedAt())
            .build();
    }
}
