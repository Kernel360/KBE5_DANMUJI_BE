package com.back2basics.project.model;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.projectstep.model.ProjectStep;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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

    // todo: 변수명 바꾸기 projectStatus
    private ProjectStatus status;

    private List<ProjectStep> steps; // = new ArrayList<>;

    private List<Assignment> assignments;

    @Builder
    public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
        boolean isDeleted, ProjectStatus status, List<ProjectStep> steps,
        List<Assignment> assignments) {
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
        this.assignments =
            assignments != null ? new ArrayList<>(assignments) : new ArrayList<>();
    }

    // todo: 조회 시 steps 세팅해주는데 먼가 맘에 안듦. 위에서 값 초기화를 해주는거 같은데 안먹혀서 일단 해놓음
    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    public void setUsers(List<Assignment> assignments) {
        this.assignments = assignments;
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