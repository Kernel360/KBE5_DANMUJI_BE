package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.out.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProjectService implements DeleteProjectUseCase {

    private final ProjectRepositoryPort projectRepositoryPort;
    private final ProjectValidator projectValidator;

    // todo : softDelete 의견 공유
    @Override
    public void deleteProject(Long id) {
        Project project = projectValidator.findProjectById(id);
        project.softDeleted();
        projectRepositoryPort.update(project);
    }
}
