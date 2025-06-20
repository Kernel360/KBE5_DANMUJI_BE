package com.back2basics.domain.inquiry.dto.request;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.inquiry.port.in.command.UpdateInquiryStatusCommand;
import lombok.Getter;

@Getter
public class UpdateInquiryStatusByAdminRequest {

    private InquiryStatus status;

    public UpdateInquiryStatusCommand toCommand() {
        return UpdateInquiryStatusCommand.builder()
            .status(status)
            .build();
    }

}
