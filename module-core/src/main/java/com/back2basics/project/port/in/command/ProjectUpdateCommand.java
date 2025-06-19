package com.back2basics.project.port.in.command;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectUpdateCommand {

    @NotBlank(message = "프로젝트명은 필수입니다.")
    String name;

    String description;

    LocalDate startDate;

    LocalDate endDate;

    List<Long> devManagerId; // 담당자

    List<Long> clientManagerId;

    List<Long> devUserId; // 멤버

    List<Long> clientUserId;
}
