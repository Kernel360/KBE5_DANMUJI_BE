package com.back2basics.cleaner;

import com.back2basics.SoftDeletableCleaner;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyCleaner implements SoftDeletableCleaner {

    private final CompanyEntityRepository repository;

    @Override
    public void clean(LocalDateTime threshold) {
        repository.deleteByDeletedAtBefore(threshold);
    }

    @Override
    public String getName() {
        return "Company";
    }
}
