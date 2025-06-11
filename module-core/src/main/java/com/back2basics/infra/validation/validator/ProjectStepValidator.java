package com.back2basics.infra.validation.validator;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProjectStepValidator {

    private final ReadProjectStepPort port;

    public ProjectStep findProjectStep(Long id) {
        return port.findById(id);
    }
}