package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.ChecklistStatus;
import java.time.LocalDateTime;
import java.util.List;

public record ChecklistDetailResult(Long id, Long stepId, Long userId, String name, String username,
                                    ChecklistStatus status, String title, String content,
                                    LocalDateTime createdAt, LocalDateTime completedAt,
                                    List<ApprovalResult> approvals) {

}
