package com.back2basics.infra.validation.validator;

import static com.back2basics.infra.exception.user.UserErrorCode.DUPLICATE_USERNAME;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserQueryPort userQueryPort;

    public void validateDuplicateUsername(String username) {
        boolean exists = userQueryPort.existsByUsername(username);
        if (exists) {
            throw new UserException(DUPLICATE_USERNAME);
        }
    }

    public void validateNotFoundUsername(String username) {
        boolean exists = userQueryPort.existsByUsername(username);
        if (!exists) {
            throw new UserException(USER_NOT_FOUND);
        }
    }

    public void validateNotFoundUserId(Long userId) {
        boolean exists = userQueryPort.existsById(userId);
        if (!exists) {
            throw new UserException(USER_NOT_FOUND);
        }
    }

    public void validateAllUsersExist(List<Long> userIds) {
        List<Long> existing = userQueryPort.findAllByIds(userIds)
            .stream()
            .map(User::getId)
            .toList();

        List<Long> notFound = userIds.stream()
            .filter(id -> !existing.contains(id))
            .toList();

        if (!notFound.isEmpty()) {
            throw new UserException(USER_NOT_FOUND);
        }
    }
}
