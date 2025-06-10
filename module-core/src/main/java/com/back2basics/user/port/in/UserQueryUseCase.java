package com.back2basics.user.port.in;

import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserSimpleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryUseCase {

    UserInfoResult getUserInfo(Long userId);

    Page<UserSimpleResult> getAllUsers(Pageable pageable);

    boolean existsByUsername(String username);
}
