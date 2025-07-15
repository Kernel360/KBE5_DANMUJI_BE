package com.back2basics.cleaner;

import com.back2basics.SoftDeletableCleaner;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyCleaner implements SoftDeletableCleaner {

    private final CompanyEntityRepository repository;
    private final UserEntityRepository userEntityRepository;

    @Override
    public void clean(LocalDateTime threshold) {
        // 1. 삭제 대상 회사 목록 조회
        List<Long> deletedCompanyIds = repository.findIdsByDeletedAtBefore(threshold);

        if (!deletedCompanyIds.isEmpty()) {
            // 2. 해당 회사에 소속된 사용자 삭제
            userEntityRepository.deleteAllByCompanyIdIn(deletedCompanyIds);

            // 3. 회사 삭제
            repository.deleteByIdIn(deletedCompanyIds);
        }
    }

    @Override
    public String getName() {
        return "Company";
    }
}
