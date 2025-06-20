package com.back2basics.inquiry.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateInquiryCommand {

    private String title;
    private String content;

}
