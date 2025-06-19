package com.back2basics.adapter.persistence.inquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryEntityRepository extends JpaRepository<InquiryEntity, Long> {

    Page<InquiryEntity> findByDeletedAtIsNull(Pageable pageable);

}
