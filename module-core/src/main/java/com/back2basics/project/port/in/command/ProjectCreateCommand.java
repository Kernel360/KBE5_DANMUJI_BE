package com.back2basics.project.port.in.command;

import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.User;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ProjectCreateCommand {

    @NotBlank(message = "프로젝트명은 필수입니다.")
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<ProjectUser> projectUsers; // user, companyType, userType 들어감
}

// todo: 임시용. CreateProjectUserCommand를 따로 할 지 고민. ProjectUser가 아니라
