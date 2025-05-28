package com.back2basics.user.port.in;

import com.back2basics.user.service.result.UserInfoResult;

public interface GetUserUseCase {

    UserInfoResult getUserInfo(Long userId);

}
