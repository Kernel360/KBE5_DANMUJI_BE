package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadProjectPort {

    Optional<Project> findById(Long id);

    Project findProjectById(Long id);

    Page<Project> findAll(Pageable pageable);

    List<Project> getAllProjects();

    Page<Project> findAllByUserId(Long userId, Pageable pageable);

    Page<Project> searchByKeyword(String keyword, Pageable pageable);

    List<Project> getRecentProjects();

    // 양방향
    Page<Project> findAllByUserIdTwo(Long userId, Pageable pageable);

    // 단방향
    Page<Project> findAllByUserIdOne(Long userId, Pageable pageable);

    boolean existsById(Long id);
}
