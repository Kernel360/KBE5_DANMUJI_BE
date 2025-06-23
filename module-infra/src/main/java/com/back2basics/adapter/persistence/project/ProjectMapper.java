package com.back2basics.adapter.persistence.project;

import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    // todo: mapper 통일

    @Autowired @Lazy
    private ProjectStepMapper projectStepMapper;

    @Autowired @Lazy
    private AssignmentMapper assignmentMapper;

    private final ProjectEntityRepository projectEntityRepository;

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
            .progress(projectEntity.getProgress())
            .build();
    }

    public ProjectEntity toEntity(Project project) {
        ProjectEntity projectEntity = projectEntityRepository.findById(project.getId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
        return ProjectEntity.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .steps(project.getSteps().stream().map(step -> projectStepMapper.toEntity(step, projectEntity)).toList())
            .assignments(
                project.getAssignments().stream().map(assignmentMapper::toEntityTest).toList())
            .progress(project.getProgress())
            .build();
    }

    // 프로젝트 생성 mapper
    public ProjectEntity toCreateEntity(Project project) {
        return ProjectEntity.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .steps(project.getSteps().stream().map(projectStepMapper::toCreateEntity).toList())
            .assignments(
                project.getAssignments().stream().map(assignmentMapper::toEntityTest).toList())
            .progress(project.getProgress())
            .build();
    }
}
