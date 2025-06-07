package com.back2basics.question.port.out;

public interface QuestionStatusUpdatePort {

    void markAsAnswered(Long questionId, String userIp);

    void markAsResolved(Long questionId, String userIp);
}
