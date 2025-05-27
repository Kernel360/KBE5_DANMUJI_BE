package com.back2basics.service.user;

import com.back2basics.infra.user.validation.UserValidator;
import com.back2basics.model.user.Role;
import com.back2basics.model.user.User;
import com.back2basics.port.in.user.ChangePasswordUseCase;
import com.back2basics.port.in.user.CreateUserUseCase;
import com.back2basics.port.in.user.DeleteUserUseCase;
import com.back2basics.port.in.user.GetUserUseCase;
import com.back2basics.port.in.user.UpdateUserUseCase;
import com.back2basics.port.out.user.UserRepositoryPort;
import com.back2basics.service.user.command.ChangePasswordCommand;
import com.back2basics.service.user.command.UserCreateCommand;
import com.back2basics.service.user.command.UserUpdateCommand;
import com.back2basics.service.user.result.UserCreateResult;
import com.back2basics.service.user.result.UserInfoResult;
import com.back2basics.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CreateUserUseCase,
    UpdateUserUseCase,
    DeleteUserUseCase,
    GetUserUseCase,
    ChangePasswordUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserValidator userValidator;
    private final PasswordGenerator passwordGenerator;

    @Override
    public UserCreateResult createUser(UserCreateCommand command) {
        userValidator.validateDuplicateUsername(command.getUsername());
        String generatedPassword = passwordGenerator.generate();

        User user = User.builder()
            .username(command.getUsername())
            .password(generatedPassword)
            .email(command.getEmail())
            .name(command.getName())
            .phone(command.getPhone())
            .position(command.getPosition())
            .role(Role.USER)
            .isDeleted(false)
            .build();

        User saved = userRepositoryPort.save(user);
        return UserCreateResult.builder()
            .id(saved.getId())
            .username(saved.getUsername())
            .password(generatedPassword)
            .build();
    }

    @Override
    public void updateUser(Long userId, UserUpdateCommand command) {
        User user = userValidator.findUserById(userId);
        user.updateUser(command.getUsername(), command.getName(), command.getEmail(),
            command.getPhone(), command.getPosition());

        userRepositoryPort.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userValidator.findUserById(userId);
        user.markDeleted();
        userRepositoryPort.save(user);
    }

    @Override
    public UserInfoResult getUser(Long userId) {
        User user = userValidator.findUserById(userId);
        return UserInfoResult.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .build();
    }

    @Override
    public void changePassword(Long userId, ChangePasswordCommand command) {
        User user = userValidator.findUserById(userId);
        userValidator.validateCurrentPassword(user, command.getCurrentPassword());

        user.updatePassword(command.getNewPassword());
        userRepositoryPort.save(user);
    }
}
