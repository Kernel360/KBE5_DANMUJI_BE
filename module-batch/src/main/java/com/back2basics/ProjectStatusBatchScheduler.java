package com.back2basics;

import com.back2basics.project.port.in.UpdateProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStatusBatchScheduler {

    private final UpdateProjectUseCase updateUseCase;

    @Scheduled(cron = "0 0 1 * * *")
    public void run() {
        updateUseCase.updateDueSoonAndDelayedProjects();
    }
}
