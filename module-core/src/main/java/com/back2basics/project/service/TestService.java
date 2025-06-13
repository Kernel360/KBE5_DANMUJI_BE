package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.TestUseCase;
import com.back2basics.project.port.out.TestPort;
import com.back2basics.project.service.result.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService implements TestUseCase {

    private final TestPort testPort;

    @Override
    public Page<TestResult> getAllByUserIdTwo(Long userId, Pageable pageable) {
        Page<Project> projects = testPort.findAllByUserIdTwo(userId, pageable);
        Page<TestResult> result = projects.map(TestResult::toResult);
        return result;
    }

    @Override
    public Page<TestResult> getAllByUserIdOne(Long userId, Pageable pageable) {
        Page<Project> projects = testPort.findAllByUserIdOne(userId, pageable);
        Page<TestResult> result = projects.map(TestResult::toResult);
        return result;
    }
}
