package com.back2basics.question.port.out;

import com.back2basics.question.model.Question;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionReadPort {

    Optional<Question> findById(Long id);

    Page<Question> findAllByPostId(Long postId, Pageable pageable);

    Page<Question> findAll(Pageable pageable);

}
