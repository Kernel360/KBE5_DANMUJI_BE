package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.UpdateAnswerUseCase;
import com.back2basics.answer.port.in.command.UpdateAnswerCommand;
import com.back2basics.answer.port.out.UpdateAnswerPort;
import com.back2basics.infra.exception.ForbiddenAccessException;
import com.back2basics.infra.validation.validator.AnswerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAnswerService implements UpdateAnswerUseCase {

    private final AnswerValidator answerValidator;
    private final UpdateAnswerPort updateAnswerPort;


    @Override
    public void updateAnswer(Long userId, Long answerid,
        UpdateAnswerCommand command) {
        Answer answer = answerValidator.findAnswerById(answerid);

        if (!answer.getAuthorId().equals(userId)) {
            throw new ForbiddenAccessException("자신의 답변만 삭제할 수 있습니다.");
        }

        answer.update(command);
        updateAnswerPort.update(answer);
    }
}
