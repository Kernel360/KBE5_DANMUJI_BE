package com.back2basics.service.project.validation;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ProjectRepositoryPort;
import com.back2basics.service.project.exception.ProjectErrorCode;
import com.back2basics.service.project.exception.ProjectException;
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
