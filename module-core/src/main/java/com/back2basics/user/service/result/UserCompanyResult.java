package com.back2basics.user.service.result;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;

public record UserCompanyResult(Long id, String name, Long userId, String position,
                                UserType userType,
                                Long companyId, CompanyType companyType, String companyName) {

    public static UserCompanyResult from(Assignment assignment) {
        return null;
//        return new UserCompanyResult(
//            assignment.getId(),
//            assignment.getUser().getName(),
//            assignment.getUser().getId(),
//            assignment.getUser().getPosition(),
//            assignment.getUserType(),
//            assignment.getCompany().getId(),
//            assignment.getCompanyType(),
//            assignment.getCompany().getName()
//        );
    }
}
