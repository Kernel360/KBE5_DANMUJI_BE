package com.back2basics.infra.validation.validator;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ProjectRepositoryPort;
import com.back2basics.infra.exception.project.ProjectErrorCode;
import com.back2basics.infra.exception.project.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ProjectRepositoryPort projectRepositoryPort;

    public Project findProject(Long id) {
        return projectRepositoryPort.findById(id)
            .orElseThrow(() -> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));
    }
}
