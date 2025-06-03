package com.back2basics.user.service.result;

import com.back2basics.user.model.User;
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
    private final Long companyId;

    public static UserInfoResult toResult(User user) {
        return UserInfoResult.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .companyId(user.getCompanyId())
            .build();
    }

}
