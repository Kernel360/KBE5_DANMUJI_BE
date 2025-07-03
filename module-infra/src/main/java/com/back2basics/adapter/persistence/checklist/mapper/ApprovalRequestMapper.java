package com.back2basics.adapter.persistence.checklist.mapper;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.Checklist;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalRequestMapper {

    public Checklist toDomain(ChecklistEntity entity) {
        List<Long> approverIds = entity.getResponses().stream()
            .map(response -> response.getApprover().getId())
            .toList();
        return new Checklist(entity.getId(), entity.getProjectStep().getId(),
            entity.getUser().getId(), entity.getTitle(), entity.getContent(),
            entity.getChecklistStatus(),
            entity.getRequestedAt(), entity.getCompletedAt(), approverIds);
    }

    public ChecklistEntity toEntity(Checklist request, ProjectStepEntity projectStep,
        UserEntity user, List<ApprovalResponseEntity> responses
    ) {
        return new ChecklistEntity(request.getId(), projectStep, user, request.getTitle(),
            request.getContent(), request.getChecklistStatus(), request.getRequestedAt(),
            request.getCompletedAt(), responses);
    }
}
