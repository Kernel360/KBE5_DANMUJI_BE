package com.back2basics.service.project.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateCommand {

    @NotBlank(message = "프로젝트명은 필수입니다.") // NotBlank - null, "", " " 모두 허용 안함
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
