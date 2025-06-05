package com.back2basics.domain.project.dto.request;

import com.back2basics.company.model.CompanyType;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.UserType;
import java.util.List;

public record CreateProjectUserRequest(Long userId, CompanyType companyType, UserType userType) {

    public static ProjectUser toDomain(CreateProjectUserRequest request) {
        return ProjectUser.builder()
            .userId(request.userId())
            .companyType(request.companyType)
            .userType(request.userType)
            .build();
    }

    public static List<ProjectUser> toDomainList(List<CreateProjectUserRequest> request) {
        return request.stream()
            .map(CreateProjectUserRequest::toDomain).toList();
    }

}
