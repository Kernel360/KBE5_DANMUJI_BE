package com.back2basics.adapter.persistence.company;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findByIdAndDeletedAtIsNull(Long id);

    // 삭제 안 된 데이터만 페이징 조회
    Page<CompanyEntity> findByDeletedAtIsNull(Pageable pageable);

    Page<CompanyEntity> findByNameContainingAndDeletedAtIsNull(Pageable pageable, String keyword);
}