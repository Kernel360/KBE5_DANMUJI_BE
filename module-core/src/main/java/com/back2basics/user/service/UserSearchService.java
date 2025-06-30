package com.back2basics.user.service;

import com.back2basics.user.port.in.UserSearchUseCase;
import com.back2basics.user.port.out.UserSearchPort;
import com.back2basics.user.service.result.UserSummaryResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSearchService implements UserSearchUseCase {

    private final UserSearchPort userSearchPort;

    @Override
    @Transactional(readOnly = true)
    public List<UserSummaryResult> searchUsersByProjectId(Long projectId) {
        return userSearchPort.searchUsersByProjectId(projectId).stream()
            .map(UserSummaryResult::from) // ← 간결하게 변환
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummaryResult searchUserByUsername(String username) {
        return UserSummaryResult.from(userSearchPort.searchUserByUsername(username));
    }
}
