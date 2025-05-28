package com.back2basics.domain.project.dto.response;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectGetResult;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectGetResponse(Long id, String name, String description,
                                 LocalDate startDate, LocalDate endDate, LocalDateTime createdAt,
                                 LocalDateTime updatedAt, LocalDateTime deletedAt,
                                 boolean isDeleted, ProjectStatus status) {

    public static ProjectGetResponse toResponse(ProjectGetResult result) {
        return new ProjectGetResponse(result.getId(), result.getName(), result.getDescription(),
            result.getStartDate(), result.getEndDate(), result.getCreatedAt(),
            result.getUpdatedAt(), result.getDeletedAt(), result.isDeleted(), result.getStatus());
    }
}
