package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.ChecklistStatus;
import java.time.LocalDateTime;

public record ChecklistInfoResult(Long id, Long stepId, Long userId, String username,
                                  String title, String content,
                                  ChecklistStatus status, LocalDateTime createdAt
) {

}