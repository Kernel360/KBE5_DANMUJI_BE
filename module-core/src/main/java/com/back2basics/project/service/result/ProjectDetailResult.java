package com.back2basics.project.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.service.result.UserCompanyResult;
import java.time.LocalDate;
import java.util.List;

public record ProjectDetailResult(Long id, String name, String description, LocalDate startDate,
                                  LocalDate endDate, String status,
                                  List<UserCompanyResult> clients,
                                  List<UserCompanyResult> developers,
                                  List<ProjectStepSimpleResult> steps) {

    public static ProjectDetailResult of(Project project, List<ProjectStep> projectSteps,
        List<ProjectUser> users) {

        List<ProjectStepSimpleResult> stepResults = projectSteps.stream()
            .map(ProjectStepSimpleResult::from)
            .toList();

        List<UserCompanyResult> clientUsers = users.stream()
            .filter(user -> user.getCompanyType().equals(CompanyType.CLIENT))
            .map(UserCompanyResult::from)
            .toList();

        List<UserCompanyResult> developerUsers = users.stream()
            .filter(user -> user.getCompanyType().equals(CompanyType.DEVELOPER))
            .map(UserCompanyResult::from)
            .toList();

        return new ProjectDetailResult(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getStartDate(),
            project.getEndDate(),
            project.getStatus().name(),
            clientUsers,
            developerUsers,
            stepResults
        );
    }

}
