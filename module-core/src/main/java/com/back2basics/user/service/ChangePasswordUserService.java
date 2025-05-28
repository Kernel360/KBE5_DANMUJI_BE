package com.back2basics.user.service;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.ChangePasswordUseCase;
import com.back2basics.user.port.in.command.ChangePasswordCommand;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordUserService implements ChangePasswordUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void change(Long userId, ChangePasswordCommand command) {
        User user = userRepositoryPort.findById(userId);
        if (!user.validateCurrentPassword(command.getCurrentPassword())) {
            throw new UserException(UserErrorCode.PASSWORD_MISMATCH);
        }

        user.changePassword(command.getNewPassword());
        userRepositoryPort.save(user);
    }
}
