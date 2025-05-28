package com.back2basics.user.port.in;

import com.back2basics.user.model.User;

public interface UserQueryUseCase {

    User findByUsername(String username);
}
