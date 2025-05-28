package com.back2basics.service.project.mapper;

import com.back2basics.project.model.Project;
import com.back2basics.service.project.dto.ProjectCreateCommand;
import com.back2basics.service.project.dto.ProjectResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectCommandMapper {

    public static ProjectResponseDto from(Project project) {
        return ProjectResponseDto.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .createdAt(project.getCreatedAt())
            .updatedAt(project.getUpdatedAt())
            .deletedAt(project.getDeletedAt())
            .build();
    }

    public static Project to(ProjectCreateCommand command) {
        return Project.builder()
            .name(command.getName())
            .description(command.getDescription())
            .startDate(command.getStartDate())
            .endDate(command.getEndDate())
            .build();
    }

}
