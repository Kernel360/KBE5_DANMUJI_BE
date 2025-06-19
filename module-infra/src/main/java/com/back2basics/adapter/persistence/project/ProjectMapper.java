package com.back2basics.adapter.persistence.project;

import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    @Autowired @Lazy
    private ProjectStepMapper projectStepMapper;

    @Autowired @Lazy
    private AssignmentMapper assignmentMapper;

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
                projectEntity.getSteps().stream().map(projectStepMapper::toDomainTest).toList())
            .assignments(
                projectEntity.getAssignments().stream().map(assignmentMapper::toDomainTest)
                    .toList())
            .build();
    }

    public ProjectEntity toEntity(Project project) {
        return ProjectEntity.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .steps(project.getSteps().stream().map(projectStepMapper::toEntityTest).toList())
            .assignments(
                project.getAssignments().stream().map(assignmentMapper::toEntityTest).toList())
            .build();
    }
}
