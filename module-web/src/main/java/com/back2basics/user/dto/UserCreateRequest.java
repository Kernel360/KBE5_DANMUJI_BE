package com.back2basics.user.dto;

import lombok.Getter;

@Getter
public class UserCreateRequest {

    private String username;
    private String password;
}
