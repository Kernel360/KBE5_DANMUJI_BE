package com.back2basics.service.user;

import com.back2basics.infra.validation.UserValidator;
import com.back2basics.model.user.User;
import com.back2basics.port.in.user.CreateUserUseCase;
import com.back2basics.port.in.user.DeleteUserUseCase;
import com.back2basics.port.in.user.GetUserUseCase;
import com.back2basics.port.in.user.UpdateUserUseCase;
import com.back2basics.port.out.user.UserRepositoryPort;
import com.back2basics.service.user.dto.UserCreateCommand;
import com.back2basics.service.user.dto.UserResponseDto;
import com.back2basics.service.user.dto.UserUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CreateUserUseCase, UpdateUserUseCase, DeleteUserUseCase,
    GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserValidator userValidator;

    @Override
    public void createUser(UserCreateCommand command) {
        userValidator.validateDuplicateUsername(command.getUsername());

        User user = User.builder()
            .username(command.getUsername())
            .password(command.getPassword())
            .build();

        userRepositoryPort.save(user);
    }

    @Override
    public void updateUser(Long userId, UserUpdateCommand command) {
        User user = userValidator.findUserById(userId);
        user.updateUser(command.getUsername(), command.getPassword());

        userRepositoryPort.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userValidator.findUserById(userId);

        userRepositoryPort.deleteById(userId);
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        User user = userValidator.findUserById(userId);
        return UserResponseDto.from(user);
    }
}
