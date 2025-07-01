package com.back2basics.domain.project.dto.response;

import com.back2basics.project.service.result.ProjectStatusResult;
import java.time.LocalDate;

public record ProjectStatusResponse(Long id, String name, String description, LocalDate startDate,
                                    LocalDate endDate, int progress) {

    public static ProjectStatusResponse from(ProjectStatusResult result) {
        return new ProjectStatusResponse(result.id(), result.name(), result.description(),
            result.startDate(), result.endDate(), result.progress());
    }
}
