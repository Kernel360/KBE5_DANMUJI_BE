package com.back2basics.project.mapper;

import com.back2basics.model.project.Project;
import com.back2basics.project.entity.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toDomain(ProjectEntity projectEntity) {
        return Project.builder()
            .id(projectEntity.getId())
            .name(projectEntity.getName())
            .description(projectEntity.getDescription())
            .startDate(projectEntity.getStartDate())
            .endDate(projectEntity.getEndDate())

            .build();
    }

    public ProjectEntity fromDomain(Project project) {
        return ProjectEntity.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .deleteAt(project.getDeletedAt())
            .build();
    }
}
