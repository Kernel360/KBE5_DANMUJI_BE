package com.back2basics.adapter.persistence.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {

    Page<AnswerEntity> findAllByInquiryIdAndDeletedAtIsNull(Long inquiryId, Pageable pageable);

}
