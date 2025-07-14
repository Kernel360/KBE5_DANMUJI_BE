package com.back2basics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job cleanupJob;

    @Scheduled(cron = "0 0 2 * * ?")
    public void runCleanupJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(cleanupJob, jobParameters);
            log.info("[Batch] Cleanup job executed at 2AM");
        } catch (Exception e) {
            log.error("[Batch ERROR] Cleanup job failed: {}", e.getMessage(), e);
        }
    }
}