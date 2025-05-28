package com.back2basics.project.port.in.command;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateCommand {

    @NotBlank(message = "프로젝트명은 필수입니다.")
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
