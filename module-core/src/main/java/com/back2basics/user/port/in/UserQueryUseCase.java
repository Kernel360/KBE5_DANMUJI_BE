package com.back2basics.user.port.in;

import com.back2basics.user.model.User;
import com.back2basics.user.service.result.UserInfoResult;

public interface UserQueryUseCase {

    UserInfoResult getUserInfo(Long userId);

    User findByUsername(String username);
}
