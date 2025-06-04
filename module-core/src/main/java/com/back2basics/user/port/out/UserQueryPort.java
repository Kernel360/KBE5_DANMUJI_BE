package com.back2basics.user.port.out;

import com.back2basics.user.model.User;

public interface UserQueryPort {

    User findByUsername(String username);

    User findById(Long userId);

    boolean existsByUsername(String username);

}
