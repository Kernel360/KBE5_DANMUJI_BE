package com.back2basics.user.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.DeleteUserUseCase;
import com.back2basics.user.port.out.UserCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserCommandPort userCommandPort;
    private final HistoryLogService historyLogService;

    @Override
    public void delete(Long userId, Long loggedInUserId) {
        User deletedUser = userCommandPort.deleteById(userId);

        historyLogService.logDeleted(DomainType.USER, loggedInUserId, deletedUser, "회원 비활성화");
    }
}
