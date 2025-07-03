package com.back2basics.adapter.persistence.checklist.mapper;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.ApprovalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalResponseMapper {

    public ApprovalResponse toDomain(ApprovalResponseEntity entity) {
        return new ApprovalResponse(entity.getId(), entity.getApprover().getId(),
            entity.getApprovalRequest().getId(),
            entity.getMessage(), entity.getStatus(), entity.getRespondedAt());
    }

    public ApprovalResponseEntity toEntity(ApprovalResponse approvalResponse,
        ChecklistEntity approvalRequest, UserEntity approver
    ) {
        return new ApprovalResponseEntity(approvalResponse, approvalRequest, approver);
    }
}
