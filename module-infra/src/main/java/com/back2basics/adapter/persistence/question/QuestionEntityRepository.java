package com.back2basics.adapter.persistence.question;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT q FROM QuestionEntity q WHERE q.postId = :postId AND q.deletedAt IS NOT NULL")
    List<QuestionEntity> findAllQuestionsByPostId(Long postId);

    @Query("SELECT q FROM QuestionEntity q WHERE q.deletedAt IS NOT NULL")
    List<QuestionEntity> findAllNotDeleted();
}
