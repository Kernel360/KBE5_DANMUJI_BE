package com.back2basics.adapter.persistence.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT q FROM QuestionEntity q WHERE q.postId = :postId AND q.deletedAt IS NULL")
    Page<QuestionEntity> findAllQuestionsByPostId(Long postId, Pageable pageable);

    @Query("SELECT q FROM QuestionEntity q WHERE q.deletedAt IS NULL")
    Page<QuestionEntity> findAllNotDeleted(Pageable pageable);

}
