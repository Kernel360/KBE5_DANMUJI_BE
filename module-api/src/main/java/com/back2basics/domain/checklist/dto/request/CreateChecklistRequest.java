package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.port.in.command.CreateChecklistCommand;
import com.back2basics.custom.CustomStringLengthCheck;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CreateChecklistRequest(
    @NotBlank(message = "제목은 필수입니다.")
    @CustomStringLengthCheck(min = 1, max = 30, message = "제목은 1자 이상 30자 이하여야 합니다.")
    String title,

    @NotBlank(message = "내용은 필수입니다.")
    @CustomStringLengthCheck(min = 0, max = 500, message = "내용은 최대 500자까지 입력할 수 있습니다.")
    String content,

    List<Long> approvalIds) {

    public CreateChecklistCommand toCommand() {
        return new CreateChecklistCommand(this.title, this.content, this.approvalIds);
    }
}
