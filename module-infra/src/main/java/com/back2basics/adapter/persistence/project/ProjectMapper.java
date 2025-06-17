package com.back2basics.adapter.persistence.project;

import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectStepMapper projectStepMapper;

    @Autowired
    @Lazy
    private AssignmentMapper projectUserMapper;

    public Project toDomain(ProjectEntity projectEntity) {
        return Project.builder()
            .id(projectEntity.getId())
            .name(projectEntity.getName())
            .description(projectEntity.getDescription())
            .startDate(projectEntity.getStartDate())
            .endDate(projectEntity.getEndDate())
            .createdAt(projectEntity.getCreatedAt())
            .updatedAt(projectEntity.getUpdatedAt())
            .deletedAt(projectEntity.getDeletedAt())
            .isDeleted(projectEntity.isDeleted())
            .status(projectEntity.getStatus())
            .steps(
                projectEntity.getSteps().stream().map(projectStepMapper::toDomain).toList())
            .assignments(
                projectEntity.getAssignments().stream().map(projectUserMapper::toDomainTest)
                    .toList())
            .build();
    }

    // todo : toEntity로 변경
    public ProjectEntity fromDomain(Project project) {
        return ProjectEntity.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .steps(project.getSteps().stream().map(projectStepMapper::toEntity).toList())
            .assignments(
                project.getAssignments().stream().map(projectUserMapper::toEntityTest).toList())
            .build();
    }
}
