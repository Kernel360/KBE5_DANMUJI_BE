package com.back2basics.infra.validation.validator;

import com.back2basics.inquiry.port.out.ReadInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryValidator {

    private final ReadInquiryPort readInquiryPort;
//
//    public Inquiry findInquiry(Long id) {
//        return readInquiryPort.
//    }

}
