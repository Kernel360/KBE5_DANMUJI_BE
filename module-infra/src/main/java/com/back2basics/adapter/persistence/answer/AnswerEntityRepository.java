package com.back2basics.adapter.persistence.answer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {
    
    /*
        select *
        from answer a
        where a.question_id = :questionId
              and a.deleteAt is null
    */
    @Query("SELECT a FROM AnswerEntity a WHERE a.question.id = :questionId AND a.deletedAt IS NULL")
    List<AnswerEntity> findAllAnswersByQuestionId(Long questionId);

}
