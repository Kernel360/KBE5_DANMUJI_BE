package com.back2basics.security;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoryPort.findByUsername(username)
            .map(CustomUserDetails::new)
            .orElseThrow(
                () -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND.getMessage()));
    }
}
