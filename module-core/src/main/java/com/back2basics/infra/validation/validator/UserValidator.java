package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepositoryPort userRepositoryPort;

    public User findUserById(Long userId) {
        return userRepositoryPort.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    public void validateDuplicateUsername(String username) {
        boolean exists = userRepositoryPort.existsByUsername(username);
        if (exists) {
            throw new UserException(UserErrorCode.DUPLICATE_USERNAME);
        }
    }
}
