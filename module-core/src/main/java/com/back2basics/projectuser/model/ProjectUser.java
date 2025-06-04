package com.back2basics.projectuser.model;

import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectUser {

    private final Long id;
    private final Long projectId;
    private final Long userId;
    private CompanyType companyType;
    private UserType userType;

    public ProjectUser(Long id, Long projectId, Long userId, CompanyType companyType,
        UserType userType) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.companyType = companyType;
        this.userType = userType;
    }

    // todo: 생성

    // todo: 담당자 변경 (userType 변경)

    // todo: 회사 변경 (등록된 회사 삭제, 새로운 회사로 등록 또는 변경)
}
