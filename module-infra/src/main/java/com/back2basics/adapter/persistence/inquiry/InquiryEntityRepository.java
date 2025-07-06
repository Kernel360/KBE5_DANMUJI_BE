package com.back2basics.adapter.persistence.inquiry;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InquiryEntityRepository extends JpaRepository<InquiryEntity, Long> {

    Optional<InquiryEntity> findByIdAndDeletedAtIsNull(Long id);

    List<InquiryEntity> findAllByDeletedAtIsNullOrderByCreatedAtDesc();

    Page<InquiryEntity> findAllByDeletedAtIsNull(Pageable pageable);

    Page<InquiryEntity> findByAuthorIdAndDeletedAtIsNull(Long authorId, Pageable pageable);

    @Query("""SELECT COUNT(i) AS total,
              SUM(CASE WHEN i.inquiryStatus = 'WAITING' THEN 1 END) AS waiting_cnt,
              SUM(CASE WHEN i.inquiryStatus = 'ANSWERED' THEn 1 END) AS answered_cnt
              FROM InquiryEntity i WHERE i.deletedAt IS NULL""")
    Long getInquiryCounts();

}
