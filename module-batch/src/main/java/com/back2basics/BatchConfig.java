package com.back2basics;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job cleanupJob(JobRepository jobRepository,
        Step userCleanupStep,
        Step postCleanupStep,
        Step commentCleanupStep) {
        return new JobBuilder("cleanupJob", jobRepository)
            .start(userCleanupStep)
            .next(postCleanupStep)
            .next(commentCleanupStep)
            .build();
    }
}
