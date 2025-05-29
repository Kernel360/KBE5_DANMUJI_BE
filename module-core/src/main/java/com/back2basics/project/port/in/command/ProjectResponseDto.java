package com.back2basics.project.port.in.command;

import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectResponseDto {
    private  final Long id;

    private final String name;

    private final String description;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final LocalDateTime deletedAt;

    private final boolean isDeleted;

    private final ProjectStatus status;

    @Builder
    public ProjectResponseDto(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isDeleted, ProjectStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.status = status;
    }

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
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .build();
    }
}
