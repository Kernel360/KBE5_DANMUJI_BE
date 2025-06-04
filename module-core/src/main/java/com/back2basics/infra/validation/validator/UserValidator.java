package com.back2basics.infra.validation.validator;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserQueryPort userQueryPort;

    public void validateDuplicateUsername(String username) {
        boolean exists = userQueryPort.existsByUsername(username);
        if (exists) {
            throw new UserException(UserErrorCode.DUPLICATE_USERNAME);
        }
    }

}
