package com.back2basics.adapter.persistence.inquiry;

import com.back2basics.inquiry.model.InquiryCountsDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface InquiryEntityRepository extends JpaRepository<InquiryEntity, Long>,
    JpaSpecificationExecutor<InquiryEntity> {

    Optional<InquiryEntity> findByIdAndDeletedAtIsNull(Long id);

    List<InquiryEntity> findAllByDeletedAtIsNullOrderByCreatedAtDesc();

    Page<InquiryEntity> findAllByDeletedAtIsNull(Pageable pageable);

    Page<InquiryEntity> findByAuthorIdAndDeletedAtIsNull(Long authorId, Pageable pageable);

    @Query("SELECT new com.back2basics.inquiry.model.InquiryCountsDto(" +
        "COUNT(i), " +
        "SUM(CASE WHEN i.inquiryStatus = 'WAITING' THEN 1 ELSE 0 END), " +
        "SUM(CASE WHEN i.inquiryStatus = 'ANSWERED' THEN 1 ELSE 0 END)) " +
        "FROM InquiryEntity i WHERE i.deletedAt IS NULL")
    InquiryCountsDto getInquiryCounts();

    List<InquiryEntity> findTop5ByDeletedAtIsNullOrderByCreatedAtDesc();
}
