package com.back2basics.user.port.in;

import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserSimpleResult;
import java.util.List;

public interface UserQueryUseCase {

    UserInfoResult getUserInfo(Long userId);

    List<UserSimpleResult> getAllUsers();

    boolean existsByUsername(String username);
}
