package com.back2basics.adapter.persistence.approval.mapper;

import com.back2basics.adapter.persistence.approval.entity.ApprovalRequestEntity;
import com.back2basics.adapter.persistence.approval.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.approval.model.ApprovalRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalRequestMapper {

    public ApprovalRequest toDomain(ApprovalRequestEntity entity) {
        List<Long> approverIds = entity.getResponses().stream()
            .map(response -> response.getApprover().getId())
            .toList();
        return new ApprovalRequest(entity.getId(), entity.getProjectStep().getId(),
            entity.getRequester().getId(), entity.getApprovalRequestStatus(),
            entity.getRequestedAt(), entity.getCompletedAt(), approverIds);
    }

    public ApprovalRequestEntity toEntity(ApprovalRequest request, ProjectStepEntity projectStep,
        UserEntity requester, List<ApprovalResponseEntity> responses
    ) {
        return new ApprovalRequestEntity(request.getId(), projectStep, requester,
            request.getApprovalRequestStatus(), request.getRequestedAt(), request.getCompletedAt(),
            responses);
    }
}
