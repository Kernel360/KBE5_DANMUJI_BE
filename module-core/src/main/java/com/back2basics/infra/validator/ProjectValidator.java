package com.back2basics.infra.validator;

import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;

import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ReadProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ReadProjectPort port;

    public Project findById(Long id) {
        return port.findById(id)
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
    }

    public Project findProjectForRestore(Long id) {
        return port.findDeletedProject(id)
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
    }

    public void validateNotFoundProjectId(Long id) {
        boolean exists = port.existsById(id);
        if (!exists) {
            throw new ProjectException(PROJECT_NOT_FOUND);
        }
    }

}
