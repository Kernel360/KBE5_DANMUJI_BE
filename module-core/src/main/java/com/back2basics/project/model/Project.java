package com.back2basics.project.model;

import com.back2basics.post.model.PostStatus;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.projectstep.model.ProjectStep;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

// todo : enum status 추가
@Getter
public class Project {

    private final Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private boolean isDeleted;

    private ProjectStatus status;

    private List<ProjectStep> steps = new ArrayList<>();

    @Builder
    public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
        boolean isDeleted, ProjectStatus status, List<ProjectStep> steps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.status = status != null ? status : ProjectStatus.IN_PROGRESS;
        this.steps = steps != null ? new ArrayList<>(steps) : new ArrayList<>();
    }

    public void update(ProjectUpdateCommand command) {
        this.name = command.getName();
        this.description = command.getDescription();
        this.startDate = command.getStartDate();
        this.endDate = command.getEndDate();
    }

    public void statusCompleted() {
        this.status = ProjectStatus.COMPLETED;
    }

    public void statusInProgress() {
        this.status = ProjectStatus.IN_PROGRESS;
    }

    public void softDeleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}