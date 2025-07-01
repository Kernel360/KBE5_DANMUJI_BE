package com.back2basics.domain.project.dto.response;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.ProjectCountResult;

public record ProjectCountResponse(ProjectStatus projectStatus, Long statusCount) {

    public static ProjectCountResponse toResponse(ProjectCountResult result) {
        return new ProjectCountResponse(result.status(), result.count());
    }
}
