package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.project.model.Project;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectStepMapper {
    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .userId(entity.getUser().getId())
            .name(entity.getName())
            .stepStatus(entity.getStepStatus())
            .approvalStatus(entity.getApprovalStatus())
            .build();
    }

    // todo: toEntity 에서 id 필요할지 / post, user entity는 어떻게 ..?
    // todo: 어디선가 entity 건드려야 한다는게 여긴가보네 ~~~
    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        return ProjectStepEntity.builder()
            .name(projectStep.getName())
            .stepStatus(projectStep.getStepStatus())
            .approvalStatus(projectStep.getApprovalStatus())
            .build();
    }
}
