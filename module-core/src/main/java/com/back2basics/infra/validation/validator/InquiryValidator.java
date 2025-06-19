package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.inquiry.InquiryErrorCode;
import com.back2basics.infra.exception.inquiry.InquiryException;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryValidator {

    private final ReadInquiryPort readInquiryPort;

    public Inquiry findInquiry(Long id) {
        return readInquiryPort.getInquiry(id)
            .orElseThrow(() -> new InquiryException(InquiryErrorCode.INQUIRY_NOT_FOUND));
    }

}
