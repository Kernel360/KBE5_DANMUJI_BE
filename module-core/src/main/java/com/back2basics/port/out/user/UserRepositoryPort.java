package com.back2basics.port.out.user;

import com.back2basics.model.user.User;

public interface UserRepositoryPort {

  Long save(User user);
}
