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
    public List<UserSummaryResult> searchByUsernameAndProjectId(String username, Long projectId) {
        return userSearchPort.searchByUsernameAndProjectId(username, projectId).stream()
            .map(user -> new UserSummaryResult(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getRole(),
                user.getPosition()
            ))
            .toList();
    }
}
