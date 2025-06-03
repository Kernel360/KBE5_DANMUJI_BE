package com.back2basics.question.port.out;

public interface QuestionStatusUpdatePort {

    void markAsAnswered(Long questionId);

    void markAsResolved(Long questionId);
}
