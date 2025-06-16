package com.back2basics.assignment.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.assignment.model.Assignment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAssignmentResult {

    private CompanyType companyType;
    private String clientCompany;
    private String developerCompany;

    public static ReadAssignmentResult toResult(Assignment user) {
        return ReadAssignmentResult.builder()
            .developerCompany(user.getCompany().getName())
            .clientCompany(user.getCompany().getName())
            .companyType(user.getCompanyType())
            .build();
    }
}
