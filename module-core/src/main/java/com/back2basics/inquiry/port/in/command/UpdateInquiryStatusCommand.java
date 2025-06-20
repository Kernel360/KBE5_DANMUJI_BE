package com.back2basics.inquiry.port.in.command;

import com.back2basics.inquiry.model.InquiryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateInquiryStatusCommand {

    private InquiryStatus status;

}
