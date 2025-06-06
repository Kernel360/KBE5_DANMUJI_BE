package com.back2basics.projectuser.model;

import com.back2basics.company.model.Company;
import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.user.model.User;
import com.back2basics.user.model.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectUser {

    private final Long id;
    private final Project project;
    private User user;
    private Company company;
    private UserType userType;
    private CompanyType companyType;

    @Builder
    public ProjectUser(Long id, Project project, User user,
        Company company, UserType userType, CompanyType companyType) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.company = company;
        this.companyType = companyType;
        this.userType = userType;
    }

    public static ProjectUser create(Project project, User user,
        Company company, UserType userType, CompanyType companyType) {
        return ProjectUser.builder()
            .project(project)
            .user(user)
            .company(company)
            .userType(userType)
            .companyType(companyType)
            .build();
    }

    // todo: 담당자 변경 (userType 변경)

    // todo: 회사 변경 (등록된 회사 삭제, 새로운 회사로 등록 또는 변경)
}
