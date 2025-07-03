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

    private ChecklistStatus checklistStatus;

    private final LocalDateTime requestedAt;

    private LocalDateTime completedAt;

    private List<Long> responseIds;

    public Checklist(Long id, Long projectStepId, Long userId, String title, String content,
        ChecklistStatus checklistStatus, LocalDateTime requestedAt,
        LocalDateTime completedAt, List<Long> responseIds) {
        this.id = id;
        this.projectStepId = projectStepId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.checklistStatus = checklistStatus;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.responseIds = new ArrayList<>(responseIds != null ? responseIds : List.of());
    }

    public static Checklist create(Long projectStepId, Long userId, String title, String content,
        List<Long> responseIds) {
        return new Checklist(null, projectStepId, userId, title, content, ChecklistStatus.PENDING,
            LocalDateTime.now(), null, responseIds);
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
            original.completedAt,
            new ArrayList<>(original.responseIds)
        );
    }
}
