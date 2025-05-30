package com.back2basics.user.service;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.back2basics.user.service.result.UserInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {

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
            .build();
    }

    @Override
    public User findByUsername(String username) {
        return userRepositoryPort.findByUsername(username)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
