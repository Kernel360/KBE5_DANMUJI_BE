package com.back2basics.domain.inquiry.dto.request;

import com.back2basics.inquiry.port.in.command.UpdateInquiryCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateInquiryByUserRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public UpdateInquiryCommand toCommand() {
        return UpdateInquiryCommand.builder()
            .title(title)
            .content(content)
            .build();
    }

}
