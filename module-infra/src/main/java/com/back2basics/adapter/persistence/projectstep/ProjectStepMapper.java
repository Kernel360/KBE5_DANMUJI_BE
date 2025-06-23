package com.back2basics.adapter.persistence.projectstep;

import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.model.Project;
import com.back2basics.projectstep.model.ProjectStep;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepMapper {
    // todo: project를 여기서 찾아서 넣어줘도 되는지 멘토링 때 질문

    @Lazy
    private final ProjectMapper projectMapper;

    private final ProjectEntityRepository projectEntityRepository;

    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .name(entity.getName())
            .stepOrder(entity.getStepOrder())
            .projectStepStatus(entity.getProjectStepStatus())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        ProjectEntity projectEntity = projectEntityRepository.findById(projectStep.getProjectId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
        return ProjectStepEntity.builder()
            .stepId(projectStep.getStepId())
            .project(projectEntity)
            .name(projectStep.getName())
            .stepOrder(projectStep.getStepOrder())
            .projectStepStatus(projectStep.getProjectStepStatus())
            .isDeleted(projectStep.isDeleted())
            .deletedAt(projectStep.getDeletedAt())
            .build();
    }
}
