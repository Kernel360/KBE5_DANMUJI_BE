package com.back2basics.projectuser.model;

import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.user.model.User;
import com.back2basics.user.model.UserType;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectUser {

    // todo: id 말고 domain 으로 해보겠음. mapper 에서 변환시켜주려고.. 아마..
    private final Long id;
    private final Long projectId;
    private final Long userId;
    private CompanyType companyType;
    private UserType userType;

    @Builder
    public ProjectUser(Long id, CompanyType companyType, Long projectId, Long userId,
        UserType userType) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.companyType = companyType;
        this.userType = userType;
    }

    // todo: 생성 -> 이게 왜 스태틱인지ㅣ 모르겠음 . 스태틱 그냥 모르곘음 , projectUser1 변수명 변경
    // 커멘트에는 List<ProjectUser> 가 있음 .
    public static List<ProjectUser> createProjectUser(ProjectCreateCommand command,
        Project project) {
        List<ProjectUser> projectUsers = new ArrayList<>();
        for (ProjectUser projectUser : command.getProjectUsers()) {
            ProjectUser projectUser1 = ProjectUser.builder()
                .projectId(project.getId())
                .userId(projectUser.userId)
                .companyType(projectUser.companyType)
                .userType(projectUser.userType)
                .build();
            projectUsers.add(projectUser1);
        }
        return projectUsers;
    }



    // todo: 담당자 변경 (userType 변경)

    // todo: 회사 변경 (등록된 회사 삭제, 새로운 회사로 등록 또는 변경)
}
