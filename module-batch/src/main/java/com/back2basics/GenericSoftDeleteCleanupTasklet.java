package com.back2basics;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenericSoftDeleteCleanupTasklet implements Tasklet {

    private final List<SoftDeletableCleaner> cleaners;

    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution,
        @NonNull ChunkContext chunkContext) {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);

        for (SoftDeletableCleaner cleaner : cleaners) {
            try {
                cleaner.clean(threshold);
            } catch (Exception e) {
                log.error("[ERROR] Failed to clean {}: {}", cleaner.getName(), e.getMessage());
                continue;
            }
        }

        return RepeatStatus.FINISHED;
    }
}
