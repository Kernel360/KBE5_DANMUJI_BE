package com.back2basics.project.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.projectstep.service.result.ReadProjectStepResult;
import com.back2basics.projectuser.service.result.ReadProjectUserResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectGetResult {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final boolean isDeleted;
    private final ProjectStatus status;
    private final String clientCompany;
    private final String developerCompany;
    private final List<ReadProjectStepResult> steps;
    private final List<ReadProjectUserResult> projectUsers;

    public static ProjectGetResult toResult(Project project) {
        String clientCompany = project.getProjectUsers().stream()
            .filter(user -> user.getCompanyType() == CompanyType.CLIENT)
            .map(user -> user.getCompany().getName())
            .findFirst().orElse(null);

        String developerCompany = project.getProjectUsers().stream()
            .filter(user -> user.getCompanyType() == CompanyType.DEVELOPER)
            .map(user -> user.getCompany().getName())
            .findFirst().orElse(null);

        return ProjectGetResult.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .createdAt(project.getCreatedAt())
            .updatedAt(project.getUpdatedAt())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .status(project.getStatus())
            .clientCompany(clientCompany)
            .developerCompany(developerCompany)
            .steps(project.getSteps().stream()
                .map(ReadProjectStepResult::toResult)
                .collect(Collectors.toList())
            )
            .projectUsers(project.getProjectUsers().stream()
                .map(ReadProjectUserResult::toResult)
                .collect(Collectors.toList())
            )
            .build();
    }
}

