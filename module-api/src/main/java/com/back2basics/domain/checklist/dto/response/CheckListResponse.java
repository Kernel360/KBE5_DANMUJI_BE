package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.service.result.CheckListResult;
import java.time.LocalDateTime;

public record CheckListResponse(Long id, String content, Long postId, LocalDateTime createdAt,
                                boolean isChecked, LocalDateTime checkedAt) {

    public static CheckListResponse from(CheckListResult result) {
        return new CheckListResponse(result.id(), result.content(), result.postId(),
            result.createdAt(), result.isChecked(), result.checkedAt());
    }
}
