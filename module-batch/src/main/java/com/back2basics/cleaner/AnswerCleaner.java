package com.back2basics.cleaner;

import com.back2basics.SoftDeletableCleaner;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerCleaner implements SoftDeletableCleaner {

    private final AnswerEntityRepository answerEntityRepository;

    @Override
    public void clean(LocalDateTime threshold) {
        answerEntityRepository.deleteByDeletedAtBefore(threshold);
    }

    @Override
    public String getName() {
        return "Answer";
    }
}
