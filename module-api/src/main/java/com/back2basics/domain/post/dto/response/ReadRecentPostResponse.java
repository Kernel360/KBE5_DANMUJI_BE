package com.back2basics.domain.post.dto.response;

import com.back2basics.post.service.result.ReadRecentPostResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadRecentPostResponse {

    private final Long postId;
    private final String title;
    private final LocalDateTime createdAt;

    public static ReadRecentPostResponse toResponse(ReadRecentPostResult result) {
        return ReadRecentPostResponse.builder()
            .postId(result.getId())
            .title(result.getTitle())
            .createdAt(result.getCreatedAt())
            .build();
    }

    public static List<ReadRecentPostResponse> toResponse(List<ReadRecentPostResult> result) {
        return result.stream()
            .map(ReadRecentPostResponse::toResponse)
            .collect(Collectors.toList());
    }
}
