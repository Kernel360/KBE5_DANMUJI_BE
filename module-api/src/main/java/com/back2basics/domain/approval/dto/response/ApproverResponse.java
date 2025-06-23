package com.back2basics.domain.approval.dto.response;

import com.back2basics.approval.model.ApprovalResponseStatus;
import com.back2basics.approval.service.result.ApproverResult;
import java.time.LocalDateTime;
import java.util.List;

public record ApproverResponse(
    Long id,
    Long approvalRequestId,
    Long userId,
    String message,
    ApprovalResponseStatus status,
    LocalDateTime respondedAt
) {

    public static ApproverResponse from(ApproverResult result) {
        return new ApproverResponse(
            result.id(),
            result.approvalRequestId(),
            result.userId(),
            result.message(),
            result.status(),
            result.respondedAt()
        );
    }

    public static List<ApproverResponse> from(List<ApproverResult> results) {
        return results.stream()
            .map(ApproverResponse::from)
            .toList();
    }
}
