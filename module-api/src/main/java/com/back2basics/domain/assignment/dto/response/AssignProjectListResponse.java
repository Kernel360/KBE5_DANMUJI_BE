package com.back2basics.domain.assignment.dto.response;

import com.back2basics.assignment.service.result.AssignProjectListResult;
import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;

public record AssignProjectListResponse(Long id, String companyName, CompanyType companyType,
                                        String userName) {

    public static AssignProjectListResponse toResponse(AssignProjectListResult result) {
        return new AssignProjectListResponse(result.id(), result.companyName(),
            result.companyType(), result.userName());
    }
}
