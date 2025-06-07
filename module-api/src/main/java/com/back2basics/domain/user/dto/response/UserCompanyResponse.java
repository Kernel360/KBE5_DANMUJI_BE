package com.back2basics.domain.user.dto.response;

import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import com.back2basics.user.service.result.UserCompanyResult;

public record UserCompanyResponse(Long id, String name, String position, UserType userType,
                                  Long companyId, CompanyType companyType, String companyName) {

    public static UserCompanyResponse from(UserCompanyResult userCompanyResult) {
        return new UserCompanyResponse(
            userCompanyResult.id(),
            userCompanyResult.name(),
            userCompanyResult.position(),
            userCompanyResult.userType(),
            userCompanyResult.companyId(),
            userCompanyResult.companyType(),
            userCompanyResult.companyName()
        );
    }
}
