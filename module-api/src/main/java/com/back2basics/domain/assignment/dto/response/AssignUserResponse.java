package com.back2basics.domain.assignment.dto.response;

import com.back2basics.assignment.service.result.AssignUserResult;
import com.back2basics.user.model.UserType;

public record AssignUserResponse(Long id, String name, String positon, UserType userType) {

    public static AssignUserResponse toResponse(AssignUserResult result) {
        return new AssignUserResponse(result.id(), result.name(), result.position(), result.userType());
    }
}
