package com.back2basics.domain.inquiry.dto.request;

import com.back2basics.inquiry.port.in.command.CreateInquiryCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateInquiryRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public CreateInquiryCommand toCommand() {
        return CreateInquiryCommand.builder()
            .title(title)
            .content(content)
            .build();
    }
}
