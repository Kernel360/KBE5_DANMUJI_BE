package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {

    void save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    void update(Project project);
}
