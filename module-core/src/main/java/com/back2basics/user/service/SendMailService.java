package com.back2basics.user.service;

import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.user.port.in.SendMailUseCase;
import com.back2basics.user.port.in.command.SendMailCommand;
import com.back2basics.user.port.out.MailSenderPort;
import com.back2basics.user.port.out.PasswordResetTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailService implements SendMailUseCase {

    private final UserValidator userValidator;
    private final MailSenderPort mailSenderPort;
    private final PasswordResetTokenPort passwordResetTokenPort;

    @Override
    public void send(SendMailCommand command) {
        userValidator.validateNotFoundUsername(command.getUsername());
        String token = passwordResetTokenPort.createToken(command.getUsername());
//        String link = "https://danmuji.com/reset-password?token=" + token; // todo: 실제 도메인으로 변경
        String link = "http://localhost:8080/reset-password?token=" + token;
        mailSenderPort.sendMail(command.getEmail(), command.getUsername(), link);

    }
}
