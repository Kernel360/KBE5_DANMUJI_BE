package com.back2basics.project.service;

import com.back2basics.project.port.in.SearchProjectUseCase;
import com.back2basics.project.port.in.command.ProjectSearchCommand;
import com.back2basics.project.port.out.SearchProjectPort;
import com.back2basics.project.service.result.ProjectListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchProjectService implements SearchProjectUseCase {

    private final SearchProjectPort searchProjectPort;

    @Override
    public Page<ProjectListResult> searchProjects(ProjectSearchCommand command, Pageable pageable) {
        return searchProjectPort.searchByKeyword(command, pageable)
            .map(ProjectListResult::toResult);
    }

    @Override
    public Page<ProjectListResult> searchUserProjects(Long userId, ProjectSearchCommand command,
        Pageable pageable) {
        return searchProjectPort.searchByKeywordAndUserId(userId, command, pageable)
            .map(ProjectListResult::toResult);
    }
}
