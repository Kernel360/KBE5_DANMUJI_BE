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
        // todo
        //  userCommandPort 리턴타입을 void -> User로 바꾼 이유
        //  repository에 반영된 직후 id값을 가진 인스턴스인 deletedUser 값을 넣어주지 않으면
        //  mongodb에 필드가null인 애가 들어가서 에러남
        //  연관 수정 : 이 곳의 userCommandPort.deleteById(userId), adapter 구현체에서 수정.
        historyLogService.logDeleted(DomainType.USER, loggedInUserId, deletedUser, "회원 비활성화");
    }
}
