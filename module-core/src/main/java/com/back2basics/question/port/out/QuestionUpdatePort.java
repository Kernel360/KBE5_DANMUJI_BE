package com.back2basics.question.port.out;

import com.back2basics.question.model.Question;

public interface QuestionUpdatePort {

    void update(Question question, String userIp);
}
