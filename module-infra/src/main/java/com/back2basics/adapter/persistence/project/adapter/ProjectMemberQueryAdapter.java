package com.back2basics.adapter.persistence.project.adapter;

import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.project.port.out.ProjectMemberQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectMemberQueryAdapter implements ProjectMemberQueryPort {

    private final AssignmentEntityRepository assignmentRepository;

    @Override
    public List<Long> getUserIdsByProject(Long projectId) {
        return assignmentRepository.findUserIdsByProjectId(projectId);
    }
}
