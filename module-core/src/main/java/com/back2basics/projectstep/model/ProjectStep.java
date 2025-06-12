package com.back2basics.projectstep.model;

import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectStep {

    private final Long stepId;

    private String name;

    private final Long projectId;

    private Long userId;

    private User user;

    private int stepOrder;

    private ProjectStepStatus projectStepStatus;

    private ProjectFeedbackStepStatus projectFeedbackStepStatus;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    @Builder
    public ProjectStep(Long stepId, String name, Long projectId, Long userId, User user,
        int stepOrder, ProjectStepStatus projectStepStatus,
        ProjectFeedbackStepStatus projectFeedbackStepStatus, boolean isDeleted,
        LocalDateTime deletedAt) {
        this.stepId = stepId;
        this.name = name;
        this.projectId = projectId;
        this.userId = userId;
        this.user = user;
        this.stepOrder = stepOrder;
        this.projectStepStatus = projectStepStatus;
        this.projectFeedbackStepStatus = projectFeedbackStepStatus;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    // todo: user 도메인으로 변경, user, userId 둘 다 가지고 있음 나중에 변경
    public static ProjectStep create(String name, Long projectId, Long userId,
        Integer stepOrder, ProjectStepStatus projectStepStatus) {
        return ProjectStep.builder()
            .name(name)
            .projectId(projectId)
            .userId(userId)
            .stepOrder(stepOrder)
            .projectStepStatus(projectStepStatus)
            .isDeleted(false)
            .deletedAt(null)
            .build();
    }

    // todo: 일단 name, userId 만 변경
    public void update(UpdateProjectStepCommand command) {
        this.name = command.getName();
//        this.stepOrder = command.getStepOrder();
        this.userId = command.getUserId();
        //this.projectStepStatus = command.;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    /* todo: 승인자랑 일치하는 지는 프론트에서 하시고 userid랑 로그인 id랑 같을때만 승인 버튼 보이게
        switch문으로 해도 괜찮을 듯 (리팩토링) */
    public void approvalProjectFeedbackStepStatus(
        ProjectFeedbackStepStatus projectFeedbackStepStatus) {
        this.projectFeedbackStepStatus = projectFeedbackStepStatus;
        if (projectFeedbackStepStatus == ProjectFeedbackStepStatus.APPROVED) {
            this.projectStepStatus = ProjectStepStatus.IN_PROGRESS;
        } else if (projectFeedbackStepStatus == ProjectFeedbackStepStatus.REJECTED) {
            this.projectStepStatus = ProjectStepStatus.PENDING;
        }
    }
}
