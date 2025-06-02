package com.back2basics.question.port.out;

import com.back2basics.question.model.Question;

public interface QuestionCreatePort {

    Long save(Question question);
}
