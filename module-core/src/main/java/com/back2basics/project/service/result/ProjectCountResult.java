package com.back2basics.project.service.result;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.model.StatusCountProjection;

public record ProjectCountResult(ProjectStatus status, Long count) {

    public static ProjectCountResult toResult(StatusCountProjection projection) {
        return new ProjectCountResult(projection.getProjectStatus(), projection.getCount());
    }
}
