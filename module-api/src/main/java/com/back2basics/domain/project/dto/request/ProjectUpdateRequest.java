package com.back2basics.domain.project.dto.request;

import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public record ProjectUpdateRequest(@NotBlank(message = "프로젝트명은 필수입니다.") String name,
                                   String description, String projectCost, LocalDate startDate, LocalDate endDate,
                                   List<Long> devManagerId, List<Long> clientManagerId,
                                   List<Long> devUserId,
                                   List<Long> clientUserId) {

    public ProjectUpdateCommand toCommand() {
        return ProjectUpdateCommand.builder()
            .name(name)
            .description(description)
            .projectCost(projectCost)
            .startDate(startDate)
            .endDate(endDate)
            .devManagerId(devManagerId)
            .clientManagerId(clientManagerId)
            .devUserId(devUserId)
            .clientUserId(clientUserId)
            .build();
    }
}
