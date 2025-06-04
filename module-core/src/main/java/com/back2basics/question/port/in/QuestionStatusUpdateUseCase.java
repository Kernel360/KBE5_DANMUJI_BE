package com.back2basics.question.port.in;


public interface QuestionStatusUpdateUseCase {

    void markAsAnswered(Long userId, Long questionId);

    void markAsResolved(Long userId, Long questionId);
}
