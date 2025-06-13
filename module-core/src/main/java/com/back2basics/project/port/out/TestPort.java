package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestPort {

    // 양방향
    Page<Project> findAllByUserIdTwo(Long userId, Pageable pageable);

    // 단방향
    Page<Project> findAllByUserIdOne(Long userId, Pageable pageable);
}
