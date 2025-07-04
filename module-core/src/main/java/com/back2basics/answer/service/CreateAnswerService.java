package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.CreateAnswerUseCase;
import com.back2basics.answer.port.in.command.CreateAnswerCommand;
import com.back2basics.answer.port.out.CreateAnswerPort;
import com.back2basics.infra.validator.AnswerValidator;
import com.back2basics.infra.validator.InquiryValidator;
import com.back2basics.infra.validator.QuestionValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAnswerService implements CreateAnswerUseCase {

    private final CreateAnswerPort createAnswerPort;
    private final QuestionValidator questionValidator;
    private final AnswerValidator answerValidator;
    private final UserQueryPort userQueryPort;
    private final InquiryValidator inquiryValidator;

    @Override
    public Long createAnswer(Long inquiryId, Long authorId, CreateAnswerCommand command) {
        Inquiry inquiry = inquiryValidator.findInquiry(inquiryId);

        Answer answer = Answer.builder()
            .inquiryId(inquiry.getId())
            .authorId(authorId)
            .content(command.getContent())
            .build();

        return createAnswerPort.save(answer);
    }

}
