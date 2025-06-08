package com.back2basics.question.port.out;

import com.back2basics.question.model.Question;

public interface QuestionStatusUpdatePort {

    void markAsAnswered(Question question);

    void markAsResolved(Question question);
}
