package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.user.service.result.UserInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {

    private final UserQueryPort userQueryPort;

    @Override
    public UserInfoResult getUserInfo(Long userId) {
        User user = userQueryPort.findById(userId);
        return new UserInfoResult(userId, user.getUsername(), user.getName(), user.getEmail(),
            user.getPhone(), user.getPosition(), user.getUserType(), user.getCompanyId(),
            user.getCreatedAt(), user.getCreatedAt());
    }

}
