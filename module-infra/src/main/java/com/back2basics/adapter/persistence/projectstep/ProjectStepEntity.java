package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.projectstep.model.ProjectStepStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "project_steps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectStepEntity extends BaseTimeEntity {

     @Id
    @Column(name = "step_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_step_status", nullable = false)
    private ProjectStepStatus projectStepStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_feedback_step_status")
    private ProjectFeedbackStepStatus projectFeedbackStepStatus;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public ProjectStepEntity(Long stepId, ProjectEntity project, UserEntity user, String name,
        int stepOrder, ProjectStepStatus projectStepStatus,
        ProjectFeedbackStepStatus projectFeedbackStepStatus, boolean isDeleted,
        LocalDateTime deletedAt) {
        this.stepId = stepId;
        this.project = project;
        this.user = user;
        this.name = name;
        this.stepOrder = stepOrder;
        this.projectStepStatus = projectStepStatus;
        this.projectFeedbackStepStatus = projectFeedbackStepStatus;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

}

