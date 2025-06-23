package com.back2basics.domain.project.dto.response;

import com.back2basics.domain.assignment.dto.response.AssignProjectListResponse;
import com.back2basics.domain.projectstep.dto.response.ProjectStepResponse;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectDetailResult;
import java.time.LocalDate;
import java.util.List;

public record ProjectDetailResponse(Long id, String name, String description, LocalDate startDate,
                                    LocalDate endDate, ProjectStatus projectStatus,
                                    int progress,
                                    List<AssignProjectListResponse> clients,
                                    List<AssignProjectListResponse> developers,
                                    List<ProjectStepResponse> steps) {

    public static ProjectDetailResponse toResponse(ProjectDetailResult result) {
        return new ProjectDetailResponse(
            result.id(),
            result.name(),
            result.description(),
            result.startDate(),
            result.endDate(),
            result.projectStatus(),
            result.progress(),
            result.clients().stream()
                .map(AssignProjectListResponse::toResponse)
                .toList(),
            result.developers().stream()
                .map(AssignProjectListResponse::toResponse)
                .toList(),
            result.steps().stream()
                .map(ProjectStepResponse::toResponse)
                .toList()
        );
    }
}
