package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.port.in.command.UpdateInquiryCommand;
import com.back2basics.inquiry.port.in.command.UpdateInquiryStatusCommand;

public interface UpdateInquiryUseCase {

    void updateByUser(Long inquiryId, Long authorId, UpdateInquiryCommand command);

    void updateByAdmin(Long inquiryId, UpdateInquiryStatusCommand command);

}
