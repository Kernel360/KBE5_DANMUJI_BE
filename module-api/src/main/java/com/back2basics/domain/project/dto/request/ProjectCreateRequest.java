package com.back2basics.domain.project.dto.request;

import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.projectuser.model.ProjectUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record ProjectCreateRequest(@NotBlank(message = "프로젝트명은 필수입니다.") String name,
                                   String description, @NotNull LocalDate startDate,
                                   LocalDate endDate, List<CreateProjectUserRequest> createProjectUserRequests
                                    ) {

    public ProjectCreateCommand toCommand() {
        return ProjectCreateCommand.builder()
            .name(name)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .projectUsers(CreateProjectUserRequest.toDomainList(createProjectUserRequests))
            .build();
    }
}
