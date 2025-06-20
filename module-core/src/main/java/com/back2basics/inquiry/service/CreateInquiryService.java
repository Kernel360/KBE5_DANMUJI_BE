package com.back2basics.inquiry.service;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.CreateInquiryUseCase;
import com.back2basics.inquiry.port.in.command.CreateInquiryCommand;
import com.back2basics.inquiry.port.out.CreateInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateInquiryService implements CreateInquiryUseCase {

    private final CreateInquiryPort createInquiryPort;

    @Override
    public Long createInquiry(CreateInquiryCommand command, Long id) {
        Inquiry inquiry = Inquiry.builder()
            .authorId(id)
            .title(command.getTitle())
            .content(command.getContent())
            .build();

        return createInquiryPort.save(inquiry);
    }

}
