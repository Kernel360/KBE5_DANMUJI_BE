package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.projectstep.model.ApprovalStatus;
import com.back2basics.projectstep.model.StepStatus;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

    // todo: userid는 연관 끊어야할지 공부
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserEntity user;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_status", nullable = false)
    private StepStatus stepStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    @Builder
    public ProjectStepEntity(Long stepId, ProjectEntity project, String name,
        StepStatus stepStatus, ApprovalStatus approvalStatus) {
        this.stepId = stepId;
        this.project = project;
       // this.user = user;
        this.name = name;
        this.stepStatus = stepStatus;
        this.approvalStatus = approvalStatus;
    }

    public void assignProjectEntity(ProjectEntity project) {
        this.project = project;
    }
}
