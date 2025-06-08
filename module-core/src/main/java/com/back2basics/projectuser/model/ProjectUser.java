package com.back2basics.projectuser.model;

import com.back2basics.company.model.Company;
import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.user.model.User;
import com.back2basics.user.model.UserType;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectUser {

    // todo: 어노테이션 사용해도 이게 안됨 -> NPE. 근데 일단 도메인이 이 port를 알면 안되나 ?
//    private static UserQueryPort userQueryPort;
//    private static CompanyValidator companyValidator;
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

    //todo: 개발사, 고객사 create로 분리하는게 좋을 지
    public static List<ProjectUser> createProjectUser(Project project, User developer, User client,
        Company developerCompany, Company clientCompany, List<User> developers,
        List<User> clients) {

        // todo: bulider 두번하는거 리팩토링하기 , it 대신 다른 변수명 사용
        List<ProjectUser> developerUsers = developers.stream()
            .map(it -> ProjectUser.builder()
                .project(project)
                .user(it)
                .company(developerCompany)
                .userType(it.getId().equals(developer.getId()) ? UserType.MANAGER : UserType.MEMBER)
                .companyType(CompanyType.DEVELOPER)
                .build())
            .toList();

        List<ProjectUser> clientUsers = clients.stream()
            .map(it -> ProjectUser.builder()
                .project(project)
                .user(it)
                .company(clientCompany)
                .userType(it.getId().equals(client.getId()) ? UserType.MANAGER : UserType.MEMBER)
                .companyType(CompanyType.CLIENT)
                .build())
            .toList();

        // todo: concat 으로 했었는데 gpt가 addAll이 나을거같다고 함 ..
        List<ProjectUser> allUsers = new ArrayList<>();
        allUsers.addAll(developerUsers);
        allUsers.addAll(clientUsers);

        return allUsers;
    }

    // todo: 담당자 변경 (userType 변경)

    // todo: 회사 변경 (등록된 회사 삭제, 새로운 회사로 등록 또는 변경)
}
