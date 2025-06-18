package com.back2basics.project.service.result;

import com.back2basics.assignment.service.result.AssignProjectListResult;
import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public record ProjectListResult(
    Long id,
    String name,
    List<AssignProjectListResult> assignClients,
    List<AssignProjectListResult> assignDevs,
    LocalDate startDate,
    LocalDate endDate,
    ProjectStatus projectStatus
) {

    public static ProjectListResult toResult(Project project) {
        List<AssignProjectListResult> assignClients = project.getAssignments().stream()
            .filter(assignment -> assignment.getCompanyType() == CompanyType.CLIENT)
            .map(AssignProjectListResult::toResult)
            .toList();

        List<AssignProjectListResult> assignDevs = project.getAssignments().stream()
            .filter(assignment -> assignment.getCompanyType() == CompanyType.DEVELOPER)
            .map(AssignProjectListResult::toResult)
            .toList();

        return new ProjectListResult(
            project.getId(),
            project.getName(),
            assignClients,
            assignDevs,
            project.getStartDate(),
            project.getEndDate(),
            project.getStatus()
        );
    }
}