package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.DeleteAnswerUseCase;
import com.back2basics.answer.port.out.DeleteAnswerPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.exception.global.ForbiddenAccessException;
import com.back2basics.infra.validator.AnswerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAnswerService implements DeleteAnswerUseCase {

    private final AnswerValidator answerValidator;
    private final DeleteAnswerPort deleteAnswerPort;
    private final HistoryLogService historyLogService;

    @Override
    public void deleteAnswer(Long userId, Long answerId) {
        Answer answer = answerValidator.findAnswerById(answerId);

        if (!answer.getAuthorId().equals(userId)) {
            throw new ForbiddenAccessException("자신의 답변만 삭제할 수 있습니다.");
        }

        answer.markDeleted();
        deleteAnswerPort.softDelete(answer);

        historyLogService.logDeleted(DomainType.INQUIRY, userId, answer, "문의 답변 삭제");
    }
}
