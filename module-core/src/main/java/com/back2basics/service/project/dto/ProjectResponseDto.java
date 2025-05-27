package com.back2basics.service.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Builder
    public ProjectResponseDto(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
