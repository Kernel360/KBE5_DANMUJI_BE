package com.back2basics.inquiry.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.exception.ForbiddenAccessException;
import com.back2basics.infra.validator.InquiryValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.UpdateInquiryUseCase;
import com.back2basics.inquiry.port.in.command.UpdateInquiryCommand;
import com.back2basics.inquiry.port.in.command.UpdateInquiryStatusCommand;
import com.back2basics.inquiry.port.out.UpdateInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInquiryService implements UpdateInquiryUseCase {

    private final UpdateInquiryPort updateInquiryPort;
    private final InquiryValidator inquiryValidator;
    private final HistoryLogService historyLogService;

    @Override
    public void updateByUser(Long inquiryId, Long authorId, UpdateInquiryCommand command) {
        Inquiry inquiry = inquiryValidator.findInquiry(inquiryId);
        Inquiry before = Inquiry.copyOf(inquiry);

        // todo : 커스텀익셉션 사용, Validator로 로직 이동
        if (!inquiry.getAuthorId().equals(authorId)) {
            throw new ForbiddenAccessException("자신의 문의만 수정할 수 있습니다.");
        }

        inquiry.updateContent(command);
        Inquiry savedInquiry = updateInquiryPort.update(inquiry);

        historyLogService.logUpdated(DomainType.INQUIRY, authorId, before, savedInquiry,
            "문의사항 정보 수정");
    }

    @Override
    public void updateByAdmin(Long inquiryId, UpdateInquiryStatusCommand command,
        Long loggedInUserId) {
        Inquiry inquiry = inquiryValidator.findInquiry(inquiryId);
        Inquiry before = Inquiry.copyOf(inquiry);

        inquiry.updateStatus(command);
        Inquiry savedInquiry = updateInquiryPort.update(inquiry);

        historyLogService.logUpdated(DomainType.INQUIRY, loggedInUserId, before, savedInquiry,
            "문의사항 상태 변경");
    }
}
