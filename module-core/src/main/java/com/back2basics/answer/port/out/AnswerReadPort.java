package com.back2basics.answer.port.out;

import com.back2basics.answer.model.Answer;
import java.util.List;
import java.util.Optional;

public interface AnswerReadPort {

    Optional<Answer> findById(Long id);

    List<Answer> findAll();
}
