package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void reset(Long userId, ResetPasswordCommand command) {
        User user = userRepositoryPort.findById(userId);
        
        user.changePassword(command.getNewPassword());
        userRepositoryPort.save(user);
    }
}
