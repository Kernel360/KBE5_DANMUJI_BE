package com.back2basics.port.in.user;

import com.back2basics.service.user.result.UserInfoResult;

public interface GetUserUseCase {

    UserInfoResult getUser(Long userId);
}
