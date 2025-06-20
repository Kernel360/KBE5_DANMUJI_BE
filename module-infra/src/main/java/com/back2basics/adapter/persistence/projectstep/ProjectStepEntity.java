package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "project_steps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectStepEntity {

    @Id
    @Column(name = "step_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_step_status", nullable = false)
    private ProjectStepStatus projectStepStatus;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public ProjectStepEntity(Long stepId, ProjectEntity project, String name,
        int stepOrder, ProjectStepStatus projectStepStatus, boolean isDeleted,
        LocalDateTime deletedAt) {
        this.stepId = stepId;
        this.project = project;
        this.name = name;
        this.stepOrder = stepOrder;
        this.projectStepStatus = projectStepStatus;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
}

