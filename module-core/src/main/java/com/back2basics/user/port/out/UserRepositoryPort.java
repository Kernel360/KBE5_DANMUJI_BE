package com.back2basics.user.port.out;

import com.back2basics.user.model.User;
import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    User findById(Long userId);

    boolean existsByUsername(String username);

    void deleteById(Long userId);

    Optional<User> findByUsername(String username);
}
