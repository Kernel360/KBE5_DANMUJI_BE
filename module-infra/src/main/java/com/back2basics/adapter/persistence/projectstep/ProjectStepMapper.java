package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepMapper {

    public final UserMapper userMapper;


    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .userId(entity.getUser() != null ? entity.getUser().getId() : null)
            .user(entity.getUser() != null ? userMapper.toDomain(entity.getUser()) : null)
            .name(entity.getName())
            .stepOrder(entity.getStepOrder())
            .projectStepStatus(entity.getProjectStepStatus())
            .projectFeedbackStepStatus(entity.getProjectFeedbackStepStatus())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        return ProjectStepEntity.builder()
            .stepId(projectStep.getStepId())
            .name(projectStep.getName())
            .stepOrder(projectStep.getStepOrder())
            .projectStepStatus(projectStep.getProjectStepStatus())
            .projectFeedbackStepStatus(projectStep.getProjectFeedbackStepStatus())
            .isDeleted(projectStep.isDeleted())
            .deletedAt(projectStep.getDeletedAt())
            .build();
    }
}
