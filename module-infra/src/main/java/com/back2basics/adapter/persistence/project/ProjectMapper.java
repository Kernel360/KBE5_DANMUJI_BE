package com.back2basics.adapter.persistence.project;

import com.back2basics.adapter.persistence.assignment.AssignmentEntity;
import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.project.model.Project;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    @Autowired @Lazy
    private ProjectStepMapper projectStepMapper;

    @Autowired @Lazy
    private AssignmentMapper assignmentMapper;

    private final ProjectStepEntityRepository projectStepEntityRepository;
    private final AssignmentEntityRepository assignmentEntityRepository;

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
            .projectStatus(projectEntity.getProjectStatus())
            .projectCost(projectEntity.getProjectCost())
            .steps(
                projectEntity.getSteps().stream().map(projectStepMapper::toDomain).toList())
            .assignments(
                projectEntity.getAssignments().stream().map(assignmentMapper::toDomain)
                    .toList())
            .progress(projectEntity.getProgress())
            .build();
    }

    public ProjectEntity toEntity(Project project) {
        List<ProjectStepEntity> stepEntities = projectStepEntityRepository.findAllByProjectIdAndDeletedAtIsNull(project.getId());
        List<AssignmentEntity> assignmentEntities = assignmentEntityRepository.findByProject_Id(project.getId());
        System.out.println("================ ProjectMapper의 project.isDeleted() = " + project.isDeleted() + " ======================");
        System.out.println("================ ProjectMapper의 project.deletedAt() = " + project.getDeletedAt() + " ======================");
        return ProjectEntity.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .isDeleted(project.isDeleted())
            .projectStatus(project.getProjectStatus())
            .projectCost(project.getProjectCost())
            .steps(stepEntities)
            .assignments(assignmentEntities)
            .progress(project.getProgress())
            .build();
    }
}
