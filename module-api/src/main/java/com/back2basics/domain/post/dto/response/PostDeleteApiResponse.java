package com.back2basics.domain.post.dto.response;

import com.back2basics.post.service.result.PostDeleteResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDeleteApiResponse {

    private final Long id;
    private final String title;
    private final LocalDateTime deletedAt;
    private final boolean isDeleted;

    public static PostDeleteApiResponse toResponse(PostDeleteResult result) {
        return PostDeleteApiResponse.builder()
            .id(result.getId())
            .title(result.getTitle())
            .isDeleted(result.isDeleted())
            .deletedAt(result.getDeletedAt())
            .build();
    }


}
