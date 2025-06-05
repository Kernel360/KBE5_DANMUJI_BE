package com.back2basics.adapter.persistence.answer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {

    @Query("SELECT a FROM AnswerEntity a WHERE a.deletedAt IS NULL")
    List<AnswerEntity> findAllAnswersNotDeleted();

    /*
        select *
        from answer a
        where a.question_id = :questionId
    */
    @Query("SELECT a FROM AnswerEntity a WHERE a.question.id = :questionId")
    List<AnswerEntity> findAllAnswersByQuestionId(Long questionId);

}
