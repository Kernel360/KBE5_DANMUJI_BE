package com.back2basics.answer.port.out;

import com.back2basics.answer.model.Answer;

public interface DeleteAnswerPort {

    void softDelete(Answer answer);
}
