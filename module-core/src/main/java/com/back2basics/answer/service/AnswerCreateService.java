package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.AnswerCreateUseCase;
import com.back2basics.answer.port.in.command.AnswerCreateCommand;
import com.back2basics.answer.port.out.AnswerCreatePort;
import com.back2basics.infra.validation.validator.AnswerValidator;
import com.back2basics.infra.validation.validator.QuestionValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerCreateService implements AnswerCreateUseCase {

    private final AnswerCreatePort answerCreatePort;
    private final QuestionValidator questionValidator;
    private final AnswerValidator answerValidator;
    private final UserQueryPort userQueryPort;

    @Override
    public Long createAnswer(Long userId, AnswerCreateCommand command) {
        User user = userQueryPort.findById(userId);
        Answer answer = Answer.builder()
            .questionId(command.getQuestionId())
            .author(user)
            .content(command.getContent())
            .parentId(command.getParentId())
            .build();

        return answerCreatePort.save(answer);
    }

}
