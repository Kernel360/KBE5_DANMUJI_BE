package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.DeleteAnswerPort;
import com.back2basics.infra.exception.answer.AnswerErrorCode;
import com.back2basics.infra.exception.answer.AnswerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAnswerJpaAdapter implements DeleteAnswerPort {

    private final AnswerEntityRepository answerRepository;


    @Override
    public void softDelete(Answer answer) {
        AnswerEntity entity = answerRepository.findById(answer.getId())
            .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));

        entity.markDeleted();
        answerRepository.save(entity);
    }

}
