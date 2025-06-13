package com.back2basics.domain.project.dto.response;

import com.back2basics.domain.projectstep.dto.response.ProjectStepSimpleResponse;
import com.back2basics.domain.user.dto.response.UserCompanyResponse;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectDetailResult;
import java.time.LocalDate;
import java.util.List;

public record ProjectDetailResponse(Long id, String name, String description, LocalDate startDate,
                                    LocalDate endDate, ProjectStatus projectStatus,
                                    List<UserCompanyResponse> clients,
                                    List<UserCompanyResponse> developers,
                                    List<ProjectStepSimpleResponse> steps) {

    public static ProjectDetailResponse from(ProjectDetailResult result) {
        return new ProjectDetailResponse(
            result.id(),
            result.name(),
            result.description(),
            result.startDate(),
            result.endDate(),
            result.projectStatus(),
            result.clients().stream()
                .map(UserCompanyResponse::from)
                .toList(),
            result.developers().stream()
                .map(UserCompanyResponse::from)
                .toList(),
            result.steps().stream()
                .map(ProjectStepSimpleResponse::from)
                .toList()
        );
    }
}
