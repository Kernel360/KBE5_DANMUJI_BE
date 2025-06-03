package com.back2basics.question.port.in;

public interface QuestionDeleteUseCase {

    void delete(Long questionId, Long requesterId);
}
