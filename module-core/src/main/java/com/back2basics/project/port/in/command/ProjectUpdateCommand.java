package com.back2basics.project.port.in.command;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectUpdateCommand {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private final Long developerId;

    private final Long clientId;
}
