package com.back2basics.project.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectCreateCommand {

    @NotBlank(message = "프로젝트명은 필수입니다.")
    String name;
    String description;
    @NotNull(message = "시작일은 필수입니다.")
    LocalDate startDate;
    @NotNull(message = "개발자 아이디는 필수입니다.")
    Long developerId;
    @NotNull(message = "클라이언트 아이디는 필수입니다.")
    Long clientId;
    @NotNull(message = "개발사 아이디는 필수입니다.")
    Long developCompanyId;
    @NotNull(message = "클라이언트사 아이디는 필수입니다.")
    Long clientCompanyId;
    LocalDate endDate;
    List<Long> developMemberId;
    List<Long> clientMemberId;
}

