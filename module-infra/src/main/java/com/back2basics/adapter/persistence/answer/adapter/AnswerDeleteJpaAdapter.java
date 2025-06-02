package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntity;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.answer.port.out.AnswerDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerDeleteJpaAdapter implements AnswerDeletePort {

    private final AnswerEntityRepository answerRepository;


    @Override
    public void delete(Long answerId) {
        AnswerEntity entity = answerRepository.findById(answerId)
            .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));

        answerRepository.delete(entity);
    }

}
