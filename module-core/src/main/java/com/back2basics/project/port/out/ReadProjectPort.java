package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadProjectPort {

    Optional<Project> findById(Long id);

    Project findProjectById(Long id);

    Page<Project> findAll(Pageable pageable);

    Page<Project> findAllByUserId(Long userId, Pageable pageable);

    Page<Project> findAllDeletedProjects(Pageable pageable);

    Page<Project> searchByKeywordAndUserId(Long userId, String keyword, Pageable pageable);

    Page<Project> searchByKeyword(String keyword, Pageable pageable);

    List<Project> getRecentProjects();

    List<Project> getAllProjects();

    boolean existsById(Long id);

    List<Project> findByStatus(Long userId, ProjectStatus status);
}
