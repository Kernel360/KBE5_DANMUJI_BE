package com.back2basics.domain.project.dto.response;

import com.back2basics.domain.assignment.dto.response.AssignProjectListResponse;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectListResult;
import java.time.LocalDate;
import java.util.List;

// todo: 뎁스 하나 더 파서 회사에 멤버 목록으로 나오도록
public record ProjectListResponse(Long id, String name,
                                  List<AssignProjectListResponse> assignClients,
                                  List<AssignProjectListResponse> assignDevs,
                                  LocalDate startDate, LocalDate endDate,
                                  ProjectStatus projectStatus) {


    public static ProjectListResponse toResponse(ProjectListResult result) {
        List<AssignProjectListResponse> assignClients = result.assignClients().stream()
            .map(AssignProjectListResponse::toResponse).toList();

        List<AssignProjectListResponse> assignDevs = result.assignDevs().stream()
            .map(AssignProjectListResponse::toResponse).toList();

        return new ProjectListResponse(result.id(), result.name(), assignClients, assignDevs,
            result.startDate(), result.endDate(), result.projectStatus());
    }
}
