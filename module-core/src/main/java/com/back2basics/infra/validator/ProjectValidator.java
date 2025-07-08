package com.back2basics.infra.validator;

import static com.back2basics.infra.exception.assignment.AssignmentErrorCode.NOT_ASSIGNMENT_USER;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;

import com.back2basics.infra.exception.assignment.AssignmentException;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ReadProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ReadProjectPort port;
    private final UserValidator userValidator;

    public Project findById(Long id) {
        Project project = port.findById(id);
        if (project != null) {
            return project;
        }
        throw new ProjectException(PROJECT_NOT_FOUND);
    }

    public Project findProjectForRestore(Long id) {
        return port.findDeletedProjectById(id)
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
    }

    // todo: 관리자 여부 검증, 프로젝트 할당 여부 검증을 메서드로 나눠야 할 지
    public Project findAssignmentsProject(Long projectId, Long userId) {
        Project project = port.findById(projectId);
        if (project == null) {
            throw new ProjectException(PROJECT_NOT_FOUND);
        }

        if (userValidator.isAdmin(userId)) {
            return project;
        }

        boolean exists = project.getAssignments().stream()
            .anyMatch(assignment -> assignment.getUser().getId().equals(userId));

        if (exists) {
            return project;
        }
        throw new AssignmentException(NOT_ASSIGNMENT_USER);
    }
}
