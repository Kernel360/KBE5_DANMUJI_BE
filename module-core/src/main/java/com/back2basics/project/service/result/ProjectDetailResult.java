package com.back2basics.project.service.result;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.service.result.AssignProjectListResult;
import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.projectstep.service.result.ProjectStepResult;
import com.back2basics.user.model.UserType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record ProjectDetailResult(Long id, String name, String description, LocalDate startDate,
                                  LocalDate endDate, ProjectStatus projectStatus,
                                  int progress, String projectCost, UserType myUserType,
                                  CompanyType myCompanyType,
                                  List<AssignProjectListResult> clients,
                                  List<AssignProjectListResult> developers,
                                  List<ProjectStepResult> steps) {

    public static ProjectDetailResult of(Project project, UserType userType,
        CompanyType companyType) {

        List<ProjectStepResult> stepResults = project.getSteps().stream()
            .filter(step -> !step.isDeleted())
            .map(ProjectStepResult::toResult)
            .toList();

        List<AssignProjectListResult> clientUsers = project.getAssignments().stream()
            .filter(assign -> assign.getCompanyType() == CompanyType.CLIENT)
            .collect(Collectors.groupingBy(Assignment::getCompanyId))
            .values().stream()
            .map(AssignProjectListResult::toResult)
            .toList();

        List<AssignProjectListResult> developerUsers = project.getAssignments().stream()
            .filter(assign -> assign.getCompanyType().equals(CompanyType.DEVELOPER))
            .collect(Collectors.groupingBy(Assignment::getCompanyId))
            .values().stream()
            .map(AssignProjectListResult::toResult)
            .toList();

        return new ProjectDetailResult(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getStartDate(),
            project.getEndDate(),
            project.getProjectStatus(),
            project.getProgress(),
            project.getProjectCost(),
            userType,
            companyType,
            clientUsers,
            developerUsers,
            stepResults
        );
    }
}
