package com.back2basics.project.service.result;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.service.result.AssignProjectListResult;
import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;
import com.back2basics.user.service.result.UserCompanyResult;
import java.security.Key;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ProjectDetailResult(Long id, String name, String description, LocalDate startDate,
                                  LocalDate endDate, ProjectStatus projectStatus,
                                  List<AssignProjectListResult> clients,
                                  List<AssignProjectListResult> developers,
                                  List<ProjectStepSimpleResult> steps) {

    public static ProjectDetailResult of(Project project) {

        List<ProjectStepSimpleResult> stepResults = project.getSteps().stream()
            .map(ProjectStepSimpleResult::toResult)
            .toList();

        // 회사 ID로 그룹핑 해서
        List<AssignProjectListResult> clientUsers = project.getAssignments().stream()
            .filter(assign -> assign.getCompanyType() == CompanyType.CLIENT)
            .collect(Collectors.groupingBy(assign -> assign.getCompany().getId()))
            .values().stream()
            .map(AssignProjectListResult::toResult)
            .toList();

        List<AssignProjectListResult> developerUsers = project.getAssignments().stream()
            .filter(assign -> assign.getCompanyType().equals(CompanyType.DEVELOPER))
            .collect(Collectors.groupingBy(assign -> assign.getCompany().getId()))
            .values().stream()
            .map(AssignProjectListResult::toResult)
            .toList();

        return new ProjectDetailResult(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getStartDate(),
            project.getEndDate(),
            project.getStatus(),
            clientUsers,
            developerUsers,
            stepResults
        );
    }
}
