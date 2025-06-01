package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.GetUserUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.back2basics.user.service.result.UserInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserInfoResult getUserInfo(Long userId) {
        User user = userRepositoryPort.findById(userId);
        return UserInfoResult.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .companyId(user.getCompany().getId())
            .build();
    }
}
