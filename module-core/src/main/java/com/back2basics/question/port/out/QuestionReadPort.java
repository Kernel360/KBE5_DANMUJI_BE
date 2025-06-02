package com.back2basics.question.port.out;

import com.back2basics.question.model.Question;
import java.util.Optional;

public interface QuestionReadPort {

    Optional<Question> findById(Long id);
}
