package com.back2basics.user.port.in;

import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserSimpleResult;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryUseCase {

    UserInfoResult getUserInfo(Long userId);

    Page<UserInfoResult> getAllUsers(Pageable pageable);

    boolean existsByUsername(String username);

    Page<UserSimpleResult> getDeletedUsers(Pageable pageable);

    Map<Long, String> getNameByIds(List<Long> userIds);

    String getNameById(Long userId);
}
