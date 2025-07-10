package com.back2basics.user.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchUserCommand {

    private Long companyId;
    private String position;
    private String name;

}
