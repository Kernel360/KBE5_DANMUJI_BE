package com.back2basics.question.service;

import com.back2basics.question.port.in.QuestionReadUseCase;
import com.back2basics.question.port.out.QuestionReadPort;
import com.back2basics.question.service.result.QuestionResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionReadService implements QuestionReadUseCase {

    private final QuestionReadPort questionReadPort;

    @Override
    public List<QuestionResult> getQuestionsByPostId(Long postId) {
        return questionReadPort.findAllQuestions(postId).stream()
            .map(QuestionResult::toResult)
            .toList();
    }

    @Override
    public List<QuestionResult> getAllQuestions() {
        return questionReadPort.findAll().stream()
            .map(QuestionResult::toResult)
            .toList();
    }
}
