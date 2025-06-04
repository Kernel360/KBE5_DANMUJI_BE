package com.back2basics.user.service;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UpdateUserUseCase;
import com.back2basics.user.port.in.command.UserUpdateCommand;
import com.back2basics.user.port.out.UserCommandPort;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;

    @Override
    public void update(Long userId, UserUpdateCommand command) {
        User user = userQueryPort.findById(userId);
        user.updateUser(command.getUsername(), command.getName(), command.getEmail(),
            command.getPhone(), command.getPosition(), command.getCompanyId());

        userCommandPort.save(user);
    }

}
