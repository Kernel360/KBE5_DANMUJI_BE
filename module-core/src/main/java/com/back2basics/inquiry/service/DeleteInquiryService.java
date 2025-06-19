package com.back2basics.inquiry.service;

import com.back2basics.infra.exception.ForbiddenAccessException;
import com.back2basics.infra.validation.validator.InquiryValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.DeleteInquiryUseCase;
import com.back2basics.inquiry.port.out.DeleteInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInquiryService implements DeleteInquiryUseCase {

    private final DeleteInquiryPort deleteInquiryPort;
    private final InquiryValidator inquiryValidator;

    @Override
    public void deleteInquiry(Long inquiryId, Long userId) {

        Inquiry inquiry = inquiryValidator.findInquiry(inquiryId);

        if (!inquiry.getAuthorId().equals(userId)) {
            throw new ForbiddenAccessException("자신의 문의만 삭제할 수 있습니다.");
        }

        inquiry.markDeleted();
        deleteInquiryPort.softDelete(inquiry);
    }

}
