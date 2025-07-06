package com.back2basics.domain.project.dto.request;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.command.ProjectSearchCommand;
import java.time.LocalDate;

public record ProjectSearchRequest(String keyword, String category, ProjectStatus projectStatus, LocalDate startDate,
                                   LocalDate endDate, String sort) {
    public ProjectSearchCommand toCommand() {
        return ProjectSearchCommand.builder()
            .keyword(keyword)
            .category(category)
            .projectStatus(projectStatus)
            .startDate(startDate)
            .endDate(endDate)
            .sort(sort)
            .build();
    }
}