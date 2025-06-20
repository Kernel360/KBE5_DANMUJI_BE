package com.back2basics.adapter.persistence.answer.adapter;

import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.answer.AnswerMapper;
import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.ReadAnswerPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadAnswerJpaAdapter implements ReadAnswerPort {

    private final AnswerEntityRepository answerEntityRepository;
    private final AnswerMapper answerMapper;

    @Override
    public Optional<Answer> findById(Long answerId) {
        return answerEntityRepository.findById(answerId).map(answerMapper::toDomain);
    }

    @Override
    public Page<Answer> findAllByInquiryIdAndDeletedAtIsNull(Long inquiryId, Pageable pageable) {
        return answerEntityRepository.findAllByInquiryIdAndDeletedAtIsNull(
            inquiryId, pageable).map(answerMapper::toDomain);

    }
}
