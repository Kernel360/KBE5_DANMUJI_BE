package com.back2basics.domain.question.dto.response;

import com.back2basics.domain.answer.dto.response.AnswerResponse;
import com.back2basics.domain.user.dto.response.UserInfoResponse;
import com.back2basics.question.model.QuestionStatus;
import com.back2basics.question.service.result.QuestionResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {

    private Long id;
    private Long postId;
    private UserInfoResponse author;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;
    private List<AnswerResponse> answers;

    public static QuestionResponse toResponse(QuestionResult result) {
        List<AnswerResponse> answerResponses = result.getAnswers().stream()
            .map(AnswerResponse::toResponse)
            .collect(Collectors.toList());

        return QuestionResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .author(UserInfoResponse.from(result.getAuthor()))
            .content(result.getContent())
            .status(result.getStatus())
            .createdAt(result.getCreatedAt())
            .answers(answerResponses)
            .build();
    }
}
