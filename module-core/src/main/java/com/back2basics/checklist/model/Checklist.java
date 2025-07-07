package com.back2basics.checklist.model;

import com.back2basics.history.strategy.TargetDomain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Checklist implements TargetDomain {

    private final Long id;

    private final Long projectStepId;

    private final Long userId;

    private String title;

    private String content;

    private Boolean isDeleted = false;

    private ChecklistStatus checklistStatus;

    private final LocalDateTime requestedAt;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private List<Long> responseIds;

    public Checklist(Long id, Long projectStepId, Long userId, String title, String content,
        ChecklistStatus checklistStatus, LocalDateTime createdAt, LocalDateTime requestedAt,
        LocalDateTime completedAt, List<Long> responseIds) {
        this.id = id;
        this.projectStepId = projectStepId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.checklistStatus = checklistStatus;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.responseIds = new ArrayList<>(responseIds != null ? responseIds : List.of());
    }

    public static Checklist create(Long projectStepId, Long userId, String title, String content,
        List<Long> responseIds) {
        return new Checklist(null, projectStepId, userId, title, content, ChecklistStatus.PENDING,
            LocalDateTime.now(), LocalDateTime.now(), null, responseIds);
    }

    public void approve() {
        this.checklistStatus = ChecklistStatus.APPROVED;
        this.completedAt = LocalDateTime.now();
    }

    public void reject() {
        this.checklistStatus = ChecklistStatus.REJECTED;
        this.completedAt = LocalDateTime.now();
    }

    public void addResponse(Long approverId) {
        if (!this.responseIds.contains(approverId)) {
            this.responseIds.add(approverId);
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public static Checklist copyOf(Checklist original) {
        return new Checklist(
            original.id,
            original.projectStepId,
            original.userId,
            original.title,
            original.content,
            original.checklistStatus,
            original.requestedAt,
            original.createdAt,
            original.completedAt,
            new ArrayList<>(original.responseIds)
        );
    }
}
