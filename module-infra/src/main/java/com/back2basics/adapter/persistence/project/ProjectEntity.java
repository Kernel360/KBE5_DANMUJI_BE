package com.back2basics.adapter.persistence.project;

import com.back2basics.adapter.persistence.assignment.AssignmentEntity;
import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.project.model.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    @ColumnDefault(value = "false")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    // todo: 현재 잘못된 설계로 EAGER로 하지 않으면 에러 발생. 추후 리팩토링 하겠음
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectStepEntity> steps = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentEntity> assignments = new ArrayList<>();

    @Column(name = "progress", nullable = false)
    private int progress;

    @Builder
    public ProjectEntity(Long id, String name, String description, LocalDate startDate,
        LocalDate endDate, LocalDateTime deletedAt, boolean isDeleted, ProjectStatus status,
        List<ProjectStepEntity> steps, List<AssignmentEntity> assignments, int progress) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.status = status;
        this.steps = steps;
        this.assignments = assignments;
        this.progress = progress;
    }

    public ProjectStatus getStatus() {
        updateStatusIfDelayed();
        updateStatusIN_PROGERSS();
        return this.status;
    }

    private void updateStatusIfDelayed() {
        LocalDate today = LocalDate.now();

        if (this.status == ProjectStatus.IN_PROGRESS
            && this.endDate != null
            && this.endDate.isBefore(today)) {

            this.status = ProjectStatus.DELAY;
        }
    }

    private void updateStatusIN_PROGERSS() {
        LocalDate today = LocalDate.now();

        if (this.status == ProjectStatus.DELAY
            && this.endDate != null
            && this.endDate.isAfter(today)) {

            this.status = ProjectStatus.IN_PROGRESS;
        }
    }
}
