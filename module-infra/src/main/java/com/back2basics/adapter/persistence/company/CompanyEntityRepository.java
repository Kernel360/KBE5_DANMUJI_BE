package com.back2basics.adapter.persistence.company;

import com.back2basics.adapter.persistence.company.dto.CompanyWithUserCountProjection;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findByDeletedAtIsNull();

    Optional<CompanyEntity> findByIdAndDeletedAtIsNull(Long id);

    // 삭제 안 된 데이터만 페이징 조회
    Page<CompanyEntity> findByDeletedAtIsNull(Pageable pageable);

    @Query("""
        SELECT c.id AS id,
               c.name AS name,
               c.ceoName AS ceoName,
               c.bio AS bio,
               c.bizNo AS bizNo,
               c.zonecode AS zonecode,
               c.address AS address,
               c.email AS email,
               c.createdAt AS createdAt,
               c.tel AS tel,
               COUNT(u.id) AS userCount
        FROM CompanyEntity c
        LEFT JOIN UserEntity u ON u.company.id = c.id AND u.deletedAt IS NULL
        WHERE c.deletedAt IS NULL AND c.name LIKE %:keyword%
        GROUP BY c.id
        """)
    Page<CompanyWithUserCountProjection> findByNameContainingAndDeletedAtIsNull(Pageable pageable,
        @Param("keyword") String keyword);

    boolean existsByName(String name);

    boolean existsByBizNo(Long bizNo);

    boolean existsByAddress(String addr);

    List<CompanyEntity> findTop5ByDeletedAtIsNullOrderByCreatedAtDesc();

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByBizNoAndIdNot(Long bizNo, Long id);

    boolean existsByAddressAndIdNot(String address, Long id);

    @Query("SELECT COUNT(c) FROM CompanyEntity c WHERE c.deletedAt IS NULL")
    Long getCompanyCounts();
}