package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.command.ProjectSearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProjectPort {

    Page<Project> searchByKeyword(ProjectSearchCommand command, Pageable pageable);

    Page<Project> searchByKeywordAndUserId(Long userId, ProjectSearchCommand command,
        Pageable pageable);
}
