package com.back2basics.question.port.in;


public interface QuestionStatusUpdateUseCase {

    void markAsAnswered(Long userId, String userIp, Long questionId);

    void markAsResolved(Long userId, String userIp, Long questionId);
}
