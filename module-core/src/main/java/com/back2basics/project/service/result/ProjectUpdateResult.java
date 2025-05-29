package com.back2basics.project.service.result;

import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectUpdateResult {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final boolean isDeleted;
    private final ProjectStatus status;

    public static ProjectUpdateResult toResult(Project project) {
        return ProjectUpdateResult.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .createdAt(project.getCreatedAt())
            .updatedAt(LocalDateTime.now())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .build();
    }
}
