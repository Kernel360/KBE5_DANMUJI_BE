package com.back2basics.config;

import com.back2basics.GenericSoftDeleteCleanupTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CleanupBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final GenericSoftDeleteCleanupTasklet cleanupTasklet;

    @Bean
    public Step cleanupSoftDeletedStep() {
        return new StepBuilder("cleanupSoftDeletedStep", jobRepository)
            .tasklet(cleanupTasklet, transactionManager)
            .build();
    }

    @Bean
    public Job cleanupJob() {
        return new JobBuilder("cleanupSoftDeletedJob", jobRepository)
            .start(cleanupSoftDeletedStep())
            .build();
    }
}
