package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.port.in.command.CreateInquiryCommand;

public interface CreateInquiryUseCase {

    Long createInquiry(CreateInquiryCommand createInquiryCommand, Long id);

}
