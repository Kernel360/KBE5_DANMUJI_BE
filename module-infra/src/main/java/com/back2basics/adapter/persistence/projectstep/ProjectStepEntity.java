package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.projectstep.model.ProjectStepStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_status", nullable = false)
    private ProjectStepStatus projectStepStatus;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public ProjectStepEntity(Long stepId, Long projectId, String name, int stepOrder,
        ProjectStepStatus projectStepStatus) {
        this.stepId = stepId;
        this.projectId = projectId;
        this.name = name;
        this.stepOrder = stepOrder;
        this.projectStepStatus = projectStepStatus;
    }

}

