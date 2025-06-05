package com.back2basics.user.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.UserType;

public record UserCompanyResult(Long id, String name, String position, UserType userType,
                                Long companyId, CompanyType companyType, String companyName) {

    public static UserCompanyResult from(ProjectUser projectUser) {
        return new UserCompanyResult(
            projectUser.getId(),
            projectUser.getUser().getName(),
            projectUser.getUser().getPosition(),
            projectUser.getUserType(),
            projectUser.getCompany().getId(),
            projectUser.getCompanyType(),
            projectUser.getCompany().getName()
        );
    }
}
