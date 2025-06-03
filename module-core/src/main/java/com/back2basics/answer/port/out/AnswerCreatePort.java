package com.back2basics.answer.port.out;

import com.back2basics.answer.model.Answer;

public interface AnswerCreatePort {

    Long save(Answer answer);
}
