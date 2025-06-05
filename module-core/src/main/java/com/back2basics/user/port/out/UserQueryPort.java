package com.back2basics.user.port.out;

import com.back2basics.user.model.User;
import java.util.List;

public interface UserQueryPort {

    User findByUsername(String username);

    User findById(Long userId);

    boolean existsByUsername(String username);

    List<User> findAll();
}
