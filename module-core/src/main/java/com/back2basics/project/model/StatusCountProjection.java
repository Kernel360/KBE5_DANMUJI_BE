package com.back2basics.project.model;

public interface StatusCountProjection {
    ProjectStatus getProjectStatus();
    Long getCount();
}
