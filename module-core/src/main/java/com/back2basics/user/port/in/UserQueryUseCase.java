package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.SearchUserCommand;
import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserPositionResult;
import com.back2basics.user.service.result.UserSimpleResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryUseCase {

    UserInfoResult getUserInfo(Long userId);

    List<UserInfoResult> getAll();

    List<UserPositionResult> getAllPositions();

    Page<UserInfoResult> getAllUsers(Pageable pageable);

    boolean existsByUsername(String username);

    Page<UserSimpleResult> getDeletedUsers(Pageable pageable);

    Optional<Long> getIdByName(String userName);

    String getNameById(Long userId);

    Long getUserCounts();

    Page<UserInfoResult> searchUsers(SearchUserCommand command, Pageable pageable);
}
