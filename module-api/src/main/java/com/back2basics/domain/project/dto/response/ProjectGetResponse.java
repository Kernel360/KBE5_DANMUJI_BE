package com.back2basics.domain.project.dto.response;

import com.back2basics.assignment.service.result.ReadAssignmentResult;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.projectstep.service.result.ProjectStepResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// todo: 필요없는 response 필드 삭제
// todo: (https://www.danmuji.site/projects: id, name, clientCompany, startDate, endDate, 진행률(계산 필요), status )
public record ProjectGetResponse(Long id, String name, String description,
                                 LocalDate startDate, LocalDate endDate, LocalDateTime createdAt,
                                 LocalDateTime updatedAt, LocalDateTime deletedAt,
                                 boolean isDeleted, ProjectStatus projectStatus, int progress,
                                 List<ProjectStepResult> steps, String clientCompany,
                                 String developerCompany, List<ReadAssignmentResult> users) {

    public static ProjectGetResponse toResponse(ProjectGetResult result) {
        return new ProjectGetResponse(result.getId(), result.getName(), result.getDescription(),
            result.getStartDate(), result.getEndDate(), result.getCreatedAt(),
            result.getUpdatedAt(), result.getDeletedAt(), result.isDeleted(), result.getProjectStatus(),
            result.getProgress(), result.getSteps(), result.getClientCompany(),
            result.getDeveloperCompany(), result.getProjectUsers());
    }
}
