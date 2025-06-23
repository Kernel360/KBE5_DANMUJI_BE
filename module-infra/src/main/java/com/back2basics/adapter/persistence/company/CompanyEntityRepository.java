package com.back2basics.adapter.persistence.company;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findByDeletedAtIsNull();

    Optional<CompanyEntity> findByIdAndDeletedAtIsNull(Long id);

    // 삭제 안 된 데이터만 페이징 조회
    Page<CompanyEntity> findByDeletedAtIsNull(Pageable pageable);

    Page<CompanyEntity> findByNameContainingAndDeletedAtIsNull(Pageable pageable, String keyword);

    boolean existsByName(String name);

    boolean existsByBizNo(Long bizNo);

    boolean existsByAddress(String addr);

    List<CompanyEntity> findTop5ByDeletedAtIsNullOrderByCreatedAtDesc();

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByBizNoAndIdNot(Long bizNo, Long id);

    boolean existsByAddressAndIdNot(String address, Long id);
}