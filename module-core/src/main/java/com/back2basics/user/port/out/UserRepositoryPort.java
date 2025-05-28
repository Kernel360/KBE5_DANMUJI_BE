package com.back2basics.user.port.out;

import com.back2basics.user.model.User;
import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(Long userId);

    boolean existsByUsername(String username);
}
