package com.back2basics.user.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.in.command.UserCreateCommand;
import com.back2basics.user.port.out.UserCommandPort;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.user.service.result.UserCreateResult;
import com.back2basics.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserCommandPort userCommandPort;
    private final UserValidator userValidator;
    private final PasswordGenerator passwordGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 수정
    private final HistoryCreateService historyCreateService;
    private final UserQueryPort userQueryPort;

    @Override
    public UserCreateResult create(UserCreateCommand command, Long loggedInUserId) {
        userValidator.validateDuplicateUsername(command.getUsername());
        String generatedPassword = passwordGenerator.generate();
        String encodedPassword = bCryptPasswordEncoder.encode(generatedPassword);

        User user = User.create(command, encodedPassword);

        User saved = userCommandPort.save(user);

        User loggedInUser = userQueryPort.findById(loggedInUserId);
        historyCreateService.create(
            HistoryRequestFactory.created(DomainType.USER, loggedInUser, saved));

        return new UserCreateResult(saved.getId(), saved.getUsername(), generatedPassword);
    }
}
