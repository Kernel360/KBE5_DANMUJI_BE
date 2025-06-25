package com.back2basics.user.port.in;

public interface DeleteUserUseCase {

    void delete(Long userId, Long loggedInUserId);
}
