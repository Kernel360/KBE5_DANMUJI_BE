package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.AnswerCreateUseCase;
import com.back2basics.answer.port.in.command.AnswerCreateCommand;
import com.back2basics.answer.port.out.AnswerCreatePort;
import com.back2basics.infra.validation.validator.AnswerValidator;
import com.back2basics.infra.validation.validator.QuestionValidator;
import com.back2basics.question.model.Question;
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
    public Long createAnswer(AnswerCreateCommand command) {
        User user = userQueryPort.findById(command.getAuthorId());
        Answer answer = Answer.builder()
            .questionId(command.getQuestionId())
            .author(user)
            .content(command.getContent())
            .parentAnswerId(command.getParentId())
            .build();

        assignRelations(command, answer);

        return answerCreatePort.save(answer);
    }

    private void assignRelations(AnswerCreateCommand command, Answer answer) {
        Question question = questionValidator.findById(command.getQuestionId());
        answer.assignQuestionId(question);

        if (command.getParentId() != null) {
            Answer parentAnswer = answerValidator.findAnswerById(command.getParentId());
            parentAnswer.addChild(answer);
        } else {
            question.addAnswer(answer);
        }
    }
}
