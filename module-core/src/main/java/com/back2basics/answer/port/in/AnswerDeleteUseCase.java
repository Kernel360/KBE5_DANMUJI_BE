package com.back2basics.answer.port.in;

public interface AnswerDeleteUseCase {

    void deleteAnswer(Long userId, Long answerId);
}
