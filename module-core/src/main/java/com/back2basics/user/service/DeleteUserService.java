package com.back2basics.user.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.DeleteUserUseCase;
import com.back2basics.user.port.out.UserCommandPort;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserCommandPort userCommandPort;
    private final HistoryCreateService historyCreateService;
    private final UserQueryPort userQueryPort;

    @Override
    public void delete(Long userId, Long loggedInUserId) {

        userCommandPort.deleteById(userId);

        User loggedInUser = userQueryPort.findById(loggedInUserId);
        User deletedUser = userQueryPort.findById(userId);
        historyCreateService.create(
            HistoryRequestFactory.deleted(DomainType.USER, loggedInUser, deletedUser, null));
    }
}
