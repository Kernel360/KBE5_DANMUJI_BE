package com.back2basics.project.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.company.service.result.CompanySummaryResult;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public record ProjectListResult(
    Long id,
    String name,
    int progress,
    List<CompanySummaryResult> assignClientCompanies,
    List<CompanySummaryResult> assignDevCompanies,
    LocalDate startDate,
    LocalDate endDate,
    ProjectStatus projectStatus
) {

    public static ProjectListResult toResult(Project project) {
        List<CompanySummaryResult> assignClientCompanies = project.getAssignments().stream()
            .filter(assignment -> assignment.getCompanyType() == CompanyType.CLIENT)
            .map(CompanySummaryResult::toResult)
            .distinct().toList();

        List<CompanySummaryResult> assignDevCompanies = project.getAssignments().stream()
            .filter(assignment -> assignment.getCompanyType() == CompanyType.DEVELOPER)
            .map(CompanySummaryResult::toResult)
            .distinct().toList();

        return new ProjectListResult(
            project.getId(),
            project.getName(),
            project.getProgress(),
            assignClientCompanies,
            assignDevCompanies,
            project.getStartDate(),
            project.getEndDate(),
            project.getStatus()
        );
    }
}