package com.back2basics.assignment.service.result;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.user.model.UserType;

public record AssignUserResult(Long id, String name, String position, UserType userType) {

    public static AssignUserResult toResult(Assignment assignment) {
        return new AssignUserResult(assignment.getUserId(), assignment.getName(),
            assignment.getPosition(), assignment.getUserType());
    }
}
