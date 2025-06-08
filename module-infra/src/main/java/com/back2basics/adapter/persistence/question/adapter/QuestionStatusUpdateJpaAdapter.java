package com.back2basics.adapter.persistence.question.adapter;


import com.back2basics.adapter.persistence.question.QuestionEntityRepository;
import com.back2basics.adapter.persistence.question.QuestionMapper;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionStatusUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// todo : 얘도 그냥 업데이트 요청 하나로 묶을 수 있을지 고민
public class QuestionStatusUpdateJpaAdapter implements QuestionStatusUpdatePort {

    private final QuestionMapper mapper;
    private final QuestionEntityRepository questionRepository;


    @Override
    public void markAsAnswered(Question question) {
        questionRepository.save(mapper.toEntity(question));
    }

    @Override
    public void markAsResolved(Question question) {
        questionRepository.save(mapper.toEntity(question));
    }
}

