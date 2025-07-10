package com.back2basics;

import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.checklist.repository.ChecklistEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.file.FileEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.notification.NotificationEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CleanupStepsConfig {

    private final UserEntityRepository userRepository;
    private final CompanyEntityRepository companyRepository;
    private final ChecklistEntityRepository checklistRepository;
    private final ProjectEntityRepository projectRepository;
    private final ProjectStepEntityRepository projectStepRepository;
    private final NotificationEntityRepository notificationRepository;
    private final FileEntityRepository fileRepository;
    private final InquiryEntityRepository inquiryRepository;
    private final AnswerEntityRepository answerRepository;
    private final PostEntityRepository postRepository;
    private final QuestionEntityRepository questionRepository;
    private final CommentEntityRepository commentRepository;

    private final LocalDateTime threshold = LocalDateTime.now().minusDays(30);

    @Bean
    public Step userCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("userCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                userRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step companyCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("companyCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                companyRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step checklistCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("checklistCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                checklistRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step projectCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("projectCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                projectRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step projectStepCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("projectStepCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                projectStepRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step notificationCleanupStep(JobRepository jobRepository,
        PlatformTransactionManager tx) {
        return new StepBuilder("notificationCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                notificationRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step fileCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("fileCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                fileRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step inquiryCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("inquiryCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                inquiryRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step answerCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("answerCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                answerRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step postCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("postCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                postRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step questionCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("questionCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                questionRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }

    @Bean
    public Step commentCleanupStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("commentCleanupStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                commentRepository.deleteByDeletedAtBefore(threshold);
                return RepeatStatus.FINISHED;
            }, tx)
            .build();
    }
}