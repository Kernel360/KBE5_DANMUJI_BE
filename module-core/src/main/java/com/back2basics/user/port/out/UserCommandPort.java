package com.back2basics.user.port.out;

import com.back2basics.user.model.User;

public interface UserCommandPort {

    User save(User user);

    void deleteById(Long userId);

}
