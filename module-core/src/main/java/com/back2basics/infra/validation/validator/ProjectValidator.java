package com.back2basics.infra.validation.validator;

import com.back2basics.project.model.Project;
import com.back2basics.infra.exception.project.ProjectErrorCode;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.port.out.ReadProjectPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectValidator {
    private final ReadProjectPort port;

    public Project findProjectById(Long id) {
        return port.findById(id)
            .orElseThrow(() -> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));
    }
}
