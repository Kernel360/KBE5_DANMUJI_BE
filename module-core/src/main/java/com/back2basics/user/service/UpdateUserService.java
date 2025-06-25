package com.back2basics.user.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.user.model.Role;
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
    private final HistoryLogService historyLogService;

    @Override
    public void update(Long userId, UserUpdateCommand command, Long loggedInUserId) {
        User user = userQueryPort.findById(userId);
        User beforeUser = User.copyOf(user);
        user.updateUser(command.getUsername(), command.getName(), command.getEmail(),
            command.getPhone(), command.getPosition(), command.getCompanyId());

        userCommandPort.save(user);

        historyLogService.logUpdated(DomainType.USER, loggedInUserId, beforeUser, user,
            "회원 정보 수정");
    }

    @Override
    public void updateUserRole(Long userId, String role, Long loggedInUserId) {
        User user = userQueryPort.findById(userId);
        User beforeUser = User.copyOf(user);
        user.updateRole(Role.valueOf(role));

        userCommandPort.save(user);

        historyLogService.logUpdated(DomainType.USER, loggedInUserId, beforeUser, user,
            "회원 권한 변경");
    }

    @Override
    public void updateLastLoginAt(String username) {
        User user = userQueryPort.findByUsername(username);
        user.updateLastLoginAt();
        userCommandPort.save(user);
    }
}
