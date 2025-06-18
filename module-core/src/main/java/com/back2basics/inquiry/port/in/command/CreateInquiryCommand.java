package com.back2basics.inquiry.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateInquiryCommand {

    private String title;
    private String content;

}
