package com.back2basics.infra.validation.validator;

import static com.back2basics.infra.exception.projectstep.ProjectStepErrorCode.STEP_NOT_FOUND;

import com.back2basics.infra.exception.projectstep.ProjectStepException;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepValidator {

    private final ReadProjectStepPort readProjectStepPort;

    public void validateNotFoundStepId(Long stepId) {
        boolean exists = readProjectStepPort.existsById(stepId);
        if (!exists) {
            throw new ProjectStepException(STEP_NOT_FOUND);
        }
    }
}
