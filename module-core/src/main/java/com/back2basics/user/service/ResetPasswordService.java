package com.back2basics.user.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import com.back2basics.user.port.out.UserCommandPort;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final PasswordGenerator passwordGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HistoryCreateService historyCreateService;

    @Override
    public void reset(Long userId, ResetPasswordCommand command) {
        User user = userQueryPort.findById(userId);

        String encodedCurrentPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.changePassword(encodedCurrentPassword);
        userCommandPort.save(user);
    }

    @Override
    public String resetByAdmin(Long userId, Long loggedInUserId) {
        User user = userQueryPort.findById(userId);
        String generatedPassword = passwordGenerator.generate();

        String encodedCurrentPassword = bCryptPasswordEncoder.encode(generatedPassword);
        user.changePassword(encodedCurrentPassword);
        userCommandPort.save(user);

        User loggedInUser = userQueryPort.findById(loggedInUserId);
        historyCreateService.create(
            HistoryRequestFactory.created(DomainType.USER, loggedInUser, user));
        return generatedPassword;
    }
}
