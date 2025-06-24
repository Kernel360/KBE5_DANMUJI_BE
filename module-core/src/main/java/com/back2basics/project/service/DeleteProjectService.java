package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.out.UpdateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProjectService implements DeleteProjectUseCase {

    private final UpdateProjectPort port;
    private final ProjectValidator projectValidator;

    // todo : softDelete 의견 공유
    @Override
    public void deleteProject(Long id) {
        Project project = projectValidator.findById(id);
        project.softDeleted();
        port.update(project);
    }
}
