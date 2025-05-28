package com.back2basics.user.service;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User findByUsername(String username) {
        return userRepositoryPort.findByUsername(username)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
