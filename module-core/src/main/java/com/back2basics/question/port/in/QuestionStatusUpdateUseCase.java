package com.back2basics.question.port.in;


public interface QuestionStatusUpdateUseCase {

    void markAsAnswered(Long questionId);

    void markAsResolved(Long questionId, Long requesterId);
}
