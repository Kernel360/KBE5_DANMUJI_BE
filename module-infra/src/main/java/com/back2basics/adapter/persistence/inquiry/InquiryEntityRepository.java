package com.back2basics.adapter.persistence.inquiry;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryEntityRepository extends JpaRepository<InquiryEntity, Long> {

    Optional<InquiryEntity> findByIdAndDeletedAtIsNull(Long id);

    List<InquiryEntity> findAllByDeletedAtIsNull();

    Page<InquiryEntity> findAllByDeletedAtIsNull(Pageable pageable);

    Page<InquiryEntity> findByAuthorIdAndDeletedAtIsNull(Long authorId, Pageable pageable);

}
