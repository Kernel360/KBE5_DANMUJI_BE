package com.back2basics.adapter.persistence.checklist.mapper;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalEntity;
import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.Approval;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalMapper {

    public Approval toDomain(ApprovalEntity entity) {
        return new Approval(entity.getId(), entity.getUser().getId(),
            entity.getChecklist().getId(), entity.getMessage(), entity.getStatus(),
            entity.getRespondedAt());
    }

    public ApprovalEntity toEntity(Approval approval,
        ChecklistEntity approvalRequest, UserEntity user
    ) {
        return new ApprovalEntity(approval, approvalRequest, user);
    }
}
