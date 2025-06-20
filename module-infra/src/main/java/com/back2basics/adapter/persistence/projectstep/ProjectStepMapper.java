package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.project.model.Project;
import com.back2basics.projectstep.model.ProjectStep;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepMapper {

    private final UserMapper userMapper;

    @Lazy
    private final ProjectMapper projectMapper;


    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .project(projectMapper.toDomain(entity.getProject()))
            .name(entity.getName())
            .stepOrder(entity.getStepOrder())
            .projectStepStatus(entity.getProjectStepStatus())
            .projectFeedbackStepStatus(entity.getProjectFeedbackStepStatus())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    public ProjectStep toDomainTest(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .project(Project.builder().id(entity.getProject().getId()).build()) // 이걸 대체 뭐 해놓은거지
            .name(entity.getName())
            .stepOrder(entity.getStepOrder())
            .projectStepStatus(entity.getProjectStepStatus())
            .projectFeedbackStepStatus(entity.getProjectFeedbackStepStatus())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    // todo: toEntity 통일 또는 toEntityTest 변수명 변경
    public ProjectStepEntity toEntity(ProjectStep projectStep, ProjectEntity project) {
        return ProjectStepEntity.builder()
            .stepId(projectStep.getStepId())
            .project(project)
            .name(projectStep.getName())
            .stepOrder(projectStep.getStepOrder())
            .projectStepStatus(projectStep.getProjectStepStatus())
            .projectFeedbackStepStatus(projectStep.getProjectFeedbackStepStatus())
            .isDeleted(projectStep.isDeleted())
            .deletedAt(projectStep.getDeletedAt())
            .build();
    }

    public ProjectStepEntity toEntityTest(ProjectStep projectStep) {
        return ProjectStepEntity.builder()
            .stepId(projectStep.getStepId())
            .project(projectMapper.toEntity(projectStep.getProject()))
            .name(projectStep.getName())
            .stepOrder(projectStep.getStepOrder())
            .projectStepStatus(projectStep.getProjectStepStatus())
            .projectFeedbackStepStatus(projectStep.getProjectFeedbackStepStatus())
            .isDeleted(projectStep.isDeleted())
            .deletedAt(projectStep.getDeletedAt())
            .build();
    }
}
