package com.back2basics.adapter.persistence.approval.mapper;

import com.back2basics.adapter.persistence.approval.entity.ApprovalRequestEntity;
import com.back2basics.adapter.persistence.approval.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.approval.model.ApprovalResponse;
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
        ApprovalRequestEntity approvalRequest, UserEntity approver
    ) {
        return new ApprovalResponseEntity(approvalResponse, approvalRequest, approver);
    }
}
