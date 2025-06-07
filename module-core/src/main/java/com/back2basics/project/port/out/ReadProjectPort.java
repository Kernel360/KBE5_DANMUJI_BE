package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadProjectPort {

    Optional<Project> findById(Long id);

    Page<Project> findAll(Pageable pageable);

    Page<Project> searchByKeyword(String keyword, Pageable pageable);

}
