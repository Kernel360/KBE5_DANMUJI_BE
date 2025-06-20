package com.back2basics.answer.port.in;

public interface DeleteAnswerUseCase {

    void deleteAnswer(Long userId, Long answerId);
}
