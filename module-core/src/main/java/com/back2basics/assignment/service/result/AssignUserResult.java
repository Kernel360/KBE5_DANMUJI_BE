package com.back2basics.assignment.service.result;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.user.model.UserType;

public record AssignUserResult(Long id, String name, String position, UserType userType) {

    public static AssignUserResult toResult(Assignment assignment) {
        return new AssignUserResult(assignment.getUser().getId(), assignment.getUser().getName(),
            assignment.getUser().getPosition(), assignment.getUserType());
    }
}
