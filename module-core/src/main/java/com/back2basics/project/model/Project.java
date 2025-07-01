package com.back2basics.project.model;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.history.strategy.TargetDomain;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.projectstep.model.ProjectStep;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Project implements TargetDomain {

    private final Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private boolean isDeleted;

    private ProjectStatus projectStatus;

    private int progress;

    private String projectCost;

    private List<ProjectStep> steps;

    private List<Assignment> assignments;

    @Builder
    public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
        boolean isDeleted, ProjectStatus projectStatus, String projectCost, List<ProjectStep> steps,
        List<Assignment> assignments, Integer progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.projectStatus = projectStatus != null ? projectStatus : ProjectStatus.IN_PROGRESS;
        this.projectCost = projectCost;
        this.steps = steps != null ? new ArrayList<>(steps) : new ArrayList<>();
        this.assignments =
            assignments != null ? new ArrayList<>(assignments) : new ArrayList<>();
        this.progress = (progress != null) ? progress : 0;
    }

    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    public void setUsers(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void update(ProjectUpdateCommand command) {
        this.name = command.getName();
        this.description = command.getDescription();
        this.projectCost = command.getProjectCost();
        this.startDate = command.getStartDate();
        this.endDate = command.getEndDate();
    }

    public void statusCompleted() {
        this.projectStatus = ProjectStatus.COMPLETED;
    }

    public void statusInProgress() {
        this.projectStatus = ProjectStatus.IN_PROGRESS;
    }

    public void softDeleted() {
        if (this.isDeleted) {
            this.isDeleted = false;
            this.deletedAt = null;
        } else {
            this.isDeleted = true;
            this.deletedAt = LocalDateTime.now();
        }

    }

    public void calculateProgress(int totalStep, int completedStep) {
        double result = ((double) completedStep / totalStep) * 100;
        this.progress = (int) result;
    }

    public static Project copyOf(Project project) {
        return Project.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .createdAt(project.getCreatedAt())
            .updatedAt(project.getUpdatedAt())
            .deletedAt(project.getDeletedAt())
            .isDeleted(project.isDeleted())
            .projectStatus(project.getProjectStatus())
            .steps(project.getSteps())
            .assignments(project.getAssignments())
            .progress(project.getProgress())
            .build();
    }
}