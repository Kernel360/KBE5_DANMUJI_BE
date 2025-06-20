package com.back2basics.domain.project.dto.request;

import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

// todo: request 해서 그대로 엎어치기 하고 싶음...
public record ProjectUpdateRequest(@NotBlank(message = "프로젝트명은 필수입니다.") String name,
                                   String description, LocalDate startDate, LocalDate endDate,
                                   List<Long> devManagerId, List<Long> clientManagerId,
                                   List<Long> devUserId,
                                   List<Long> clientUserId) {

    public ProjectUpdateCommand toCommand() {
        return ProjectUpdateCommand.builder()
            .name(name)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .devManagerId(devManagerId)
            .clientManagerId(clientManagerId)
            .devUserId(devUserId)
            .clientUserId(clientUserId)
            .build();
    }
}
