package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.back2basics.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordGenerator passwordGenerator;

    @Override
    public void reset(Long userId, ResetPasswordCommand command) {
        User user = userRepositoryPort.findById(userId);

        user.changePassword(command.getNewPassword());
        userRepositoryPort.save(user);
    }

    @Override
    public String resetByAdmin(Long userId) {
        User user = userRepositoryPort.findById(userId);
        String generatedPassword = passwordGenerator.generate();
        user.changePassword(generatedPassword);
        userRepositoryPort.save(user);
        return generatedPassword;
    }
}
