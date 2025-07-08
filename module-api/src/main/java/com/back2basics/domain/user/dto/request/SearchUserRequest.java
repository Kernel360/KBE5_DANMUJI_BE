package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.SearchUserCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserRequest {

    @NotBlank(message = "업체는 필수 입력값입니다.")
    private Long companyId;

    @NotBlank(message = "직책은 필수 입력값입니다.")
    private String position;

    @Nullable
    private String name;

    public SearchUserCommand toCommand() {
        return SearchUserCommand.builder()
            .companyId(companyId)
            .position(position)
            .name(name)
            .build();
    }
}
