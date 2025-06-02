package com.back2basics.adapter.persistence.answer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {

    List<AnswerEntity> findByQuestionId(Long questionId);
}
