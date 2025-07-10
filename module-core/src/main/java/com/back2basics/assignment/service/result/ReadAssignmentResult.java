package com.back2basics.assignment.service.result;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.company.model.CompanyType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAssignmentResult {

    private CompanyType companyType;
    private String clientCompany;
    private String developerCompany;

    public static ReadAssignmentResult toResult(Assignment assignment) {
        return ReadAssignmentResult.builder()
            .developerCompany(assignment.getCompanyName())
            .clientCompany(assignment.getCompanyName())
            .companyType(assignment.getCompanyType())
            .build();
    }
}
