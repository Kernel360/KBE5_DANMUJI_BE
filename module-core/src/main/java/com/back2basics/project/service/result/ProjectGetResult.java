package com.back2basics.project.service.result;

import com.back2basics.assignment.service.result.ReadAssignmentResult;
import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.projectstep.service.result.ProjectStepResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

// todo: 필요없는 result 필드 삭제
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
    private final int progress;
    private final ProjectStatus projectStatus;
    private final String clientCompany;
    private final String developerCompany;
    private final List<ProjectStepResult> steps;
    private final List<ReadAssignmentResult> projectUsers;

    public static ProjectGetResult toResult(Project project) {
        String clientCompany = project.getAssignments().stream()
            .filter(user -> user.getCompanyType() == CompanyType.CLIENT)
            .map(user -> user.getCompany().getName())
            .findFirst().orElse(null);

        String developerCompany = project.getAssignments().stream()
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
            .projectStatus(project.getProjectStatus())
            .progress(project.getProgress())
            .clientCompany(clientCompany)
            .developerCompany(developerCompany)
            .steps(project.getSteps().stream()
                .map(ProjectStepResult::toResult)
                .collect(Collectors.toList())
            )
            .projectUsers(project.getAssignments().stream()
                .map(ReadAssignmentResult::toResult)
                .collect(Collectors.toList())
            )
            .build();
    }
}

