package com.back2basics.adapter.persistence.projectuser;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "project_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "company_type")
    private CompanyType companyType;

    @Builder
    public ProjectUserEntity(Long id, ProjectEntity project, UserEntity user, CompanyEntity company,
        CompanyType companyType,
        UserType userType) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.company = company;
        this.companyType = companyType;
        this.userType = userType;
    }

    public void assignProjectEntity(ProjectEntity project) {
        this.project = project;
    }

    public void assignUserEntity(UserEntity user) {
        this.user = user;
    }
}
