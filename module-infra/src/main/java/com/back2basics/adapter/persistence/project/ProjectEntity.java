package com.back2basics.adapter.persistence.project;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectuser.ProjectUserEntity;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.projectuser.model.ProjectUser;
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
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ProjectStepEntity> steps = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ProjectUserEntity> projectUsers = new ArrayList<>();

    @Builder
    public ProjectEntity(Long id, String name, String description, LocalDate startDate,
        LocalDate endDate, LocalDateTime deletedAt, boolean isDeleted, ProjectStatus status,
        List<ProjectStepEntity> steps, List<ProjectUserEntity> projectUsers) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.status = status;
        this.steps = steps;
        this.projectUsers = projectUsers;
    }
}
