package com.back2basics.user.port.out;

import com.back2basics.user.model.User;

public interface UserRepositoryPort {

    User save(User user);

    User findById(Long userId);

    boolean existsByUsername(String username);

    void deleteById(Long userId);
}
