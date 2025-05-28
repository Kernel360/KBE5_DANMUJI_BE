package com.back2basics.user.service;

import com.back2basics.infra.validation.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserValidator userValidator;

//    @Override
//    public void change(Long userId,
//        com.back2basics.service.user.command.ChangePasswordCommand command) {
//        User user = userValidator.findUserById(userId);
//        userValidator.validateCurrentPassword(user, command.getCurrentPassword());
//
//        user.updatePassword(command.getNewPassword());
//        userRepositoryPort.save(user);
//    }

}
