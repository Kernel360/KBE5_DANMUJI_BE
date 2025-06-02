package com.back2basics.question.service;

import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.QuestionCreateUseCase;
import com.back2basics.question.port.in.command.QuestionCreateCommand;
import com.back2basics.question.port.out.QuestionCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionCreateService implements QuestionCreateUseCase {

    private final QuestionCreatePort questionCreatePort;

    @Override
    public Long create(QuestionCreateCommand command) {
        Question question = Question.builder()
            .postId(command.getPostId())
            .authorId(command.getAuthorId())
            .content(command.getContent())
            .build();

        Long savedId = questionCreatePort.save(question);

        questionCreatePort.save(question);
        return question.getId();
    }
}
