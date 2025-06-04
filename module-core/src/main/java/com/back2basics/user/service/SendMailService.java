package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.SendMailUseCase;
import com.back2basics.user.port.in.command.SendMailCommand;
import com.back2basics.user.port.out.MailSenderPort;
import com.back2basics.user.port.out.PasswordResetTokenPort;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailService implements SendMailUseCase {

    private final UserQueryPort userQueryPort;
    private final PasswordResetTokenPort passwordResetTokenPort;
    private final MailSenderPort mailSenderPort;

    @Override
    public void sendResetLink(SendMailCommand command) {
        User user = userQueryPort.findByUsername(command.getUsername());
        String token = passwordResetTokenPort.createToken(command.getUsername());
//        String link = "https://danmuji.com/reset-password?token=" + token; // todo: 실제 도메인으로 변경
        String link = "http://localhost:8080/reset-password?token=" + token;
        mailSenderPort.sendMail(user.getEmail(), user.getUsername(), link);

    }
}
