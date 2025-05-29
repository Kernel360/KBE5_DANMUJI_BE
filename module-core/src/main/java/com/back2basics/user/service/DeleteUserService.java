package com.back2basics.user.service;

import com.back2basics.user.port.in.DeleteUserUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void delete(Long userId) {
        userRepositoryPort.deleteById(userId);
    }
}
