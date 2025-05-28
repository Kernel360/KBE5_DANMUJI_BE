package com.back2basics.port.out.project;

import com.back2basics.model.project.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {
    void save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    void update(Project project);
}
