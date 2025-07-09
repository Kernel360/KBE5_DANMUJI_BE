package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.SearchUserCommand;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserRequest {

    @Nullable
    private Long companyId;

    @Nullable
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
