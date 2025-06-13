package com.back2basics.project.port.in;

import com.back2basics.project.service.result.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TestUseCase {

    // 양방향
    Page<TestResult> getAllByUserIdTwo(Long userId, Pageable pageable);

    // 단방향
    Page<TestResult> getAllByUserIdOne(Long userId, Pageable pageable);
}
