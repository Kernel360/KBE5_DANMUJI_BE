package com.back2basics.assignment.model;

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
public class Assignment {

    private final Long id;
    private final Project project;
    private User user;
    private Company company;
    private UserType userType;
    private CompanyType companyType;

    @Builder
    public Assignment(Long id, Project project, User user,
        Company company, UserType userType, CompanyType companyType) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.company = company;
        this.companyType = companyType;
        this.userType = userType;
    }

    // todo: 빌더 말고 다른 패턴 사용도 고려
    public static Assignment create(Project project, User user,
        Company company, UserType userType, CompanyType companyType) {
        return Assignment.builder()
            .project(project)
            .user(user)
            .company(company)
            .userType(userType)
            .companyType(companyType)
            .build();
    }

    //todo: 개발사, 고객사 create로 분리하는게 좋을 지
    public static List<Assignment> createProjectUser(Project project, List<User> devManagers,
        List<User> clientManagers, List<User> devUsers, List<User> clientUsers) {

        // 개발사 멤버, 멤버가 담당자와 하나라도 일치하면 MANAGER, 참고 자료 : https://haenny.tistory.com/389
        List<Assignment> developers = devUsers.stream()
            .map(user -> Assignment.builder()
                .project(project)
                .user(user)
                .company(user.getCompany())
                .userType(
                    devManagers.stream().anyMatch(manager -> manager.getId().equals(user.getId()))
                        ? UserType.MANAGER : UserType.MEMBER)
                .companyType(CompanyType.DEVELOPER)
                .build()).toList();

        // 고객사 멤버
        List<Assignment> clients = clientUsers.stream()
            .map(user -> Assignment.builder()
                .project(project)
                .user(user)
                .company(user.getCompany())
                .userType(clientManagers.stream()
                    .anyMatch(manager -> manager.getId().equals(user.getId())) ? UserType.MANAGER
                    : UserType.MEMBER)
                .companyType(CompanyType.CLIENT)
                .build()).toList();

        List<Assignment> allUsers = new ArrayList<>();
        allUsers.addAll(developers);
        allUsers.addAll(clients);

        return allUsers;
    }

    public void toManager() {
        this.userType = UserType.MANAGER;
    }

    public void toMember() {
        this.userType = UserType.MEMBER;
    }

    // todo: 회사 변경 (등록된 회사 삭제, 새로운 회사로 등록 또는 변경)

}
