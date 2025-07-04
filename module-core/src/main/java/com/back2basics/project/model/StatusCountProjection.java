package com.back2basics.project.model;

public record StatusCountProjection(
    ProjectStatus projectStatus,
    Long count
) {

}
