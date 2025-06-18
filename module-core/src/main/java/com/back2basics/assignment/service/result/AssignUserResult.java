package com.back2basics.assignment.service.result;

import com.back2basics.assignment.model.Assignment;

public record AssignUserResult(Long id, String name, String position) {

    public static AssignUserResult toResult(Assignment assignment) {
        return new AssignUserResult(assignment.getUser().getId(), assignment.getUser().getName(),
            assignment.getUser().getPosition());
    }
}
