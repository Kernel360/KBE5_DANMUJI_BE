package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import java.util.List;
import java.util.Optional;

public interface ReadProjectPort {

    Optional<Project> findById(Long id);

    List<Project> findAll();


}
