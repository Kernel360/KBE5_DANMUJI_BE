package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UpdateUserUseCase;
import com.back2basics.user.port.in.command.UserUpdateCommand;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void update(Long userId, UserUpdateCommand command) {
        User user = userRepositoryPort.findById(userId);
        user.updateUser(command.getUsername(), command.getName(), command.getEmail(),
            command.getPhone(), command.getPosition());

        userRepositoryPort.save(user);
    }

}
