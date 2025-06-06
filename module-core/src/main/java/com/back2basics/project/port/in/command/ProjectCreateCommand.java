package com.back2basics.project.port.in.command;

import com.back2basics.projectuser.model.ProjectUser;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectCreateCommand {

    @NotBlank(message = "프로젝트명은 필수입니다.")
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long developerId; // 개발사 담당자
    private Long clientId; // 클라이언트 담당자
    private Long developCompanyId; // 개발사
    private Long clientCompanyId; // 클라이언트사

    private List<ProjectUser> projectUsers; // user, companyType, userType 들어감
}

// todo: 임시용. CreateProjectUserCommand를 따로 할 지 고민. ProjectUser가 아니라
