package com.back2basics.assignment.service.result;


import com.back2basics.assignment.model.Assignment;
import java.util.List;

public record AssignProjectListResult(Long id, String companyName,
                                      List<AssignUserResult> assignUsers) {

    public static AssignProjectListResult toResult(List<Assignment> assignments) {

        Assignment assignment = assignments.get(0);

        List<AssignUserResult> assignUsers = assignments.stream().map(AssignUserResult::toResult)
            .toList();

        return new AssignProjectListResult(assignment.getCompany().getId(),
            assignment.getCompany().getName(), assignUsers);
    }
}
