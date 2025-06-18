package com.back2basics.assignment.service.result;


import com.back2basics.assignment.model.Assignment;
import com.back2basics.company.model.CompanyType;

public record AssignProjectListResult(Long id, String companyName, CompanyType companyType,
                                      String userName) {

    public static AssignProjectListResult toResult(Assignment assignment) {

        return new AssignProjectListResult(assignment.getId(), assignment.getCompany().getName(),
            assignment.getCompanyType(), assignment.getUser().getName());
    }

}
