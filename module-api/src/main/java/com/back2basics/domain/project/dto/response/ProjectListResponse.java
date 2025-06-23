package com.back2basics.domain.project.dto.response;

import com.back2basics.domain.assignment.dto.response.AssignProjectListResponse;
import com.back2basics.domain.company.dto.response.CompanySummaryResponse;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectListResult;
import java.time.LocalDate;
import java.util.List;

// todo: 다시 뎁스 줄이기. 회사 목록의 멤버는 response 따로 요청
public record ProjectListResponse(Long projectId, String projectTitle, int progress,
                                  List<CompanySummaryResponse> assignClientCompanies,
                                  List<CompanySummaryResponse> assignDevCompanies,
                                  LocalDate startDate, LocalDate endDate,
                                  ProjectStatus projectStatus) {


    public static ProjectListResponse toResponse(ProjectListResult result) {
        List<CompanySummaryResponse> assignClientCompanies = result.assignClientCompanies().stream()
            .map(CompanySummaryResponse::toResponse).toList();

        List<CompanySummaryResponse> assignDevCompanies = result.assignDevCompanies().stream()
            .map(CompanySummaryResponse::toResponse).toList();

        return new ProjectListResponse(result.id(), result.name(), result.progress(),
            assignClientCompanies, assignDevCompanies,
            result.startDate(), result.endDate(), result.projectStatus());
    }
}
