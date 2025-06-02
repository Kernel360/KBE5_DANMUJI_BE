package com.back2basics.projectstep.service;

import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.service.result.ReadProjectStepResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectStepService implements ReadProjectStepUseCase {

    @Override
    public List<ReadProjectStepResult> findAll() {

        return List.of();
    }
}
