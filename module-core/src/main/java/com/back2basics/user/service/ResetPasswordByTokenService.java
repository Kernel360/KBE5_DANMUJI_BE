package com.back2basics.user.service;

import static com.back2basics.infra.exception.user.UserErrorCode.VERIFICATION_FAILED;

import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.ResetPasswordByTokenUseCase;
import com.back2basics.user.port.in.command.ResetPasswordByTokenCommand;
import com.back2basics.user.port.out.PasswordResetTokenPort;
import com.back2basics.user.port.out.UserCommandPort;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordByTokenService implements ResetPasswordByTokenUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenPort passwordResetTokenPort;

    @Override
    public void change(ResetPasswordByTokenCommand command) {
        String username = passwordResetTokenPort.getUsernameByToken(command.getToken());
        if (username == null) {
            throw new UserException(VERIFICATION_FAILED);
        }

        User user = userQueryPort.findByUsername(username);
        user.changePassword(passwordEncoder.encode(command.getNewPassword()));
        userCommandPort.save(user);
        passwordResetTokenPort.deleteToken(command.getToken());
    }
}
