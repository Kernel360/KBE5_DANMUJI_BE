package com.back2basics.inquiry.service;

import com.back2basics.infra.exception.ForbiddenAccessException;
import com.back2basics.infra.validation.validator.InquiryValidator;
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

    @Override
    public void updateByUser(Long inquiryId, Long authorId, UpdateInquiryCommand command) {
        Inquiry inquiry = inquiryValidator.findInquiry(inquiryId);

        if (!inquiry.getAuthorId().equals(authorId)) {
            throw new ForbiddenAccessException("자신의 문의만 수정할 수 있습니다.");
        }

        inquiry.updateContent(command);
        updateInquiryPort.update(inquiry);
    }

    @Override
    public void updateByAdmin(Long inquiryId, UpdateInquiryStatusCommand command) {

    }
}
