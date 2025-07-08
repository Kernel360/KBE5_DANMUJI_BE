package com.back2basics.assignment.model;

import com.back2basics.company.model.CompanyType;
import com.back2basics.history.strategy.TargetDomain;
import com.back2basics.project.model.Project;
import com.back2basics.user.model.UserType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Assignment implements TargetDomain {

    private final Long id;
    private final Long projectId;
    private final Long userId;
    private final Long companyId;
    private UserType userType;
    private CompanyType companyType;

    private String name;
    private String companyName;
    private String position;


    @Builder
    public Assignment(Long id, Long projectId, Long userId,
        Long companyId, UserType userType, CompanyType companyType, String name, String companyName,
        String position) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.companyId = companyId;
        this.companyType = companyType;
        this.userType = userType;
        this.name = name;
        this.companyName = companyName;
        this.position = position;
    }

    public static Assignment create(Long projectId, Long userId,
        Long companyId, UserType userType, CompanyType companyType) {
        return Assignment.builder()
            .projectId(projectId)
            .userId(userId)
            .companyId(companyId)
            .userType(userType)
            .companyType(companyType)
            .build();
    }

    //todo: 개발사, 고객사 create로 분리하는게 좋을 지
    public static List<Assignment> createProjectUser(Project project, List<Long> devManagers,
        List<Long> clientManagers, List<Long> devUsers, List<Long> clientUsers,
        List<Long> devCompanyIds, List<Long> clientCompanyIds) {

        // index(int)로 stream 사용
        List<Assignment> developers = IntStream.range(0, devUsers.size())
            .mapToObj(i -> Assignment.builder()
                .projectId(project.getId())
                .userId(devUsers.get(i))
                .companyId(devCompanyIds.get(i))
                .userType(
                    devManagers.contains(devUsers.get(i)) ? UserType.MANAGER : UserType.MEMBER)
                .companyType(CompanyType.DEVELOPER)
                .build())
            .toList();

        List<Assignment> clients = IntStream.range(0, clientUsers.size())
            .mapToObj(i -> Assignment.builder()
                .projectId(project.getId())
                .userId(clientUsers.get(i))
                .companyId(clientCompanyIds.get(i))
                .userType(
                    clientManagers.contains(clientUsers.get(i)) ? UserType.MANAGER : UserType.MEMBER
                )
                .companyType(CompanyType.CLIENT)
                .build())
            .toList();

        List<Assignment> allUsers = new ArrayList<>();
        allUsers.addAll(developers);
        allUsers.addAll(clients);

        return allUsers;
    }

    public void updateUserType(UserType userType) {
        this.userType = userType;
    }


}
