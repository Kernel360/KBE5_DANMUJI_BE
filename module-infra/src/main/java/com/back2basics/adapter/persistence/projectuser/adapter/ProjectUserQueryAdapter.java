package com.back2basics.adapter.persistence.projectuser.adapter;

import com.back2basics.adapter.persistence.projectuser.ProjectUserEntityRepository;
import com.back2basics.adapter.persistence.projectuser.ProjectUserMapper;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.projectuser.port.out.ProjectUserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUserQueryAdapter implements ProjectUserQueryPort {

    private final ProjectUserEntityRepository projectUserEntityRepository;
    private final ProjectUserMapper projectUserMapper;

    @Override
    public List<ProjectUser> findUsersByProjectId(Long projectId) {

        return projectUserEntityRepository.findByProject_Id(projectId)
            .stream()
            .map(projectUserMapper::toDomain)
            .toList();
    }
}
