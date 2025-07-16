package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.CreateAnswerUseCase;
import com.back2basics.answer.port.in.command.CreateAnswerCommand;
import com.back2basics.answer.port.out.CreateAnswerPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validator.InquiryValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.mention.MentionNotificationSender;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAnswerService implements CreateAnswerUseCase {

    private final CreateAnswerPort createAnswerPort;
    private final InquiryValidator inquiryValidator;
    private final HistoryLogService historyLogService;
    private final AnswerNotificationSender answerNotificationSender;
    private final MentionNotificationSender mentionNotificationSender;
    private final UserQueryPort userQueryPort;

    @Override
    public Long createAnswer(Long inquiryId, Long authorId, CreateAnswerCommand command) {
        Inquiry inquiry = inquiryValidator.findUsersInquiry(inquiryId);
        User adminUser = userQueryPort.findAdminByRole();

        Answer answer = Answer.builder()
            .inquiryId(inquiry.getId())
            .authorId(authorId)
            .content(command.getContent())
            .build();

        historyLogService.logCreated(DomainType.INQUIRY, authorId, inquiry, "문의 답변 완료");
        answerNotificationSender.sendNotification(adminUser.getId(),inquiry,answer);
        mentionNotificationSender.notifyMentionedUsers(adminUser.getId(),inquiry.getId(),inquiry.getId(), answer.getContent());
        return createAnswerPort.save(answer);
    }

}
