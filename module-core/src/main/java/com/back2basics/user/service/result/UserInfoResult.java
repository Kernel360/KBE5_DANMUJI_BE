package com.back2basics.user.service.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResult {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final String phone;
    private final String position;

}
