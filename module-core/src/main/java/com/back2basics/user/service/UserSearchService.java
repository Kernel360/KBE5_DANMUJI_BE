package com.back2basics.user.service;

import com.back2basics.user.port.in.UserSearchUseCase;
import com.back2basics.user.port.out.UserSearchPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService implements UserSearchUseCase {

    private final UserSearchPort userSearchPort;

    @Override
    public List<String> searchByUsername(String username) {
        return userSearchPort.searchByUsername(username);
    }
}
