package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.UpdateAnswerUseCase;
import com.back2basics.answer.port.in.command.UpdateAnswerCommand;
import com.back2basics.answer.port.out.UpdateAnswerPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.exception.global.ForbiddenAccessException;
import com.back2basics.infra.validator.AnswerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAnswerService implements UpdateAnswerUseCase {

    private final AnswerValidator answerValidator;
    private final UpdateAnswerPort updateAnswerPort;
    private final HistoryLogService historyLogService;


    @Override
    public void updateAnswer(Long userId, Long answerid,
        UpdateAnswerCommand command) {
        Answer answer = answerValidator.findAnswerById(answerid);
        Answer before = Answer.copyOf(answer);

        if (!answer.getAuthorId().equals(userId)) {
            throw new ForbiddenAccessException("자신의 답변만 수정할 수 있습니다.");
        }

        answer.update(command);
        updateAnswerPort.update(answer);

        historyLogService.logUpdated(DomainType.INQUIRY, userId, before, answer, "문의 답변 수정");
    }
}
