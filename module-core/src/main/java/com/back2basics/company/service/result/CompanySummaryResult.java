package com.back2basics.company.service.result;


import com.back2basics.assignment.model.Assignment;

public record CompanySummaryResult(Long id, String companyName) {

    public static CompanySummaryResult toResult(Assignment assignment) {
        return new CompanySummaryResult(assignment.getId(), assignment.getCompany().getName());
    }
}
