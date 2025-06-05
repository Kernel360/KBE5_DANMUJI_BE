package com.back2basics.domain.project.dto.request;

import com.back2basics.project.port.in.command.ProjectCreateCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ProjectCreateRequest(@NotBlank(message = "프로젝트명은 필수입니다.") String name,
                                   String description, @NotNull LocalDate startDate,
                                   Long developerId, Long clientId,
                                   Long developCompanyId, Long clientCompanyId,
                                   LocalDate endDate
) {

    public ProjectCreateCommand toCommand() {
        return ProjectCreateCommand.builder()
            .name(name)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .developerId(developerId)
            .clientId(clientId)
            .developCompanyId(developCompanyId)
            .clientCompanyId(clientCompanyId)
            .build();
    }
}
