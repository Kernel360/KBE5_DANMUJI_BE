package com.back2basics.domain.project.dto.request;

import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ProjectUpdateRequest(@NotBlank(message = "프로젝트명은 필수입니다.") String name,
                                   String description, LocalDate startDate, LocalDate endDate) {

    public ProjectUpdateCommand toCommand() {
        return ProjectUpdateCommand.builder()
            .name(name)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }
}
