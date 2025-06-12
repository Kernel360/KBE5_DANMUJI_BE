package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.service.result.ReadProjectStepResult;

public record ReadProjectStepResponse(Long id, String name, Long userId, String userName) {

    public static ReadProjectStepResponse toResponse(ReadProjectStepResult result) {

        return new ReadProjectStepResponse(result.getId(), result.getName(), result.getUserId(),
            result.getUser().getName());
    }
}
