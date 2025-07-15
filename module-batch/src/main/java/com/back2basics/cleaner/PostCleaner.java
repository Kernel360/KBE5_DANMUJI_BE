package com.back2basics.cleaner;

import com.back2basics.SoftDeletableCleaner;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCleaner implements SoftDeletableCleaner {

    private final PostEntityRepository repository;

    @Override
    public void clean(LocalDateTime threshold) {
        repository.deleteByDeletedAtBefore(threshold);
    }

    @Override
    public String getName() {
        return "Post";
    }
}
