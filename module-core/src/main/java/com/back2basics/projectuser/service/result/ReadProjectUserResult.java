package com.back2basics.projectuser.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.projectuser.model.ProjectUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadProjectUserResult {

    private CompanyType companyType;
    private String clientCompany;
    private String developerCompany;

    public static ReadProjectUserResult toResult(ProjectUser user) {
        return ReadProjectUserResult.builder()
            .developerCompany(user.getCompany().getName())
            .clientCompany(user.getCompany().getName())
            .companyType(user.getCompanyType())
            .build();
    }
}
