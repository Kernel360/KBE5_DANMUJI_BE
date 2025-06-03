package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.AnswerCreatePort;
import com.back2basics.infra.exception.answer.AnswerErrorCode;
import com.back2basics.infra.exception.answer.AnswerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerCreateJpaAdapter implements AnswerCreatePort {

    private final AnswerEntityRepository answerRepository;
    private final AnswerMapper mapper;

    @Override
    public Long save(Answer answer) {
        AnswerEntity entity = mapper.toEntity(answer);

        if (answer.getParentAnswerId() != null) {
            AnswerEntity parent = answerRepository.findById(answer.getParentAnswerId())
                .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));
            entity.assignParentAnswer(parent);
        }

        return answerRepository.save(entity).getId();
    }

}
