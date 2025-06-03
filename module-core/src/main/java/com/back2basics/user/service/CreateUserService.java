package com.back2basics.user.service;

import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.user.model.Role;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.in.command.UserCreateCommand;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.back2basics.user.service.result.UserCreateResult;
import com.back2basics.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserValidator userValidator;
    private final PasswordGenerator passwordGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 수정

    @Override
    public UserCreateResult create(UserCreateCommand command) {
        userValidator.validateDuplicateUsername(command.getUsername());
        String generatedPassword = passwordGenerator.generate();
        String encodedPassword = bCryptPasswordEncoder.encode(generatedPassword);

        User user = User.create(command, encodedPassword);

        User saved = userRepositoryPort.save(user);
        return UserCreateResult.builder()
            .id(saved.getId())
            .username(saved.getUsername())
            .password(generatedPassword)
            .build();
    }
}
