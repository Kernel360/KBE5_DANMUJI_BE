package com.back2basics.security.jwt;

import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserQueryUseCase userQueryUseCase;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider,
        UserQueryUseCase userQueryUseCase) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userQueryUseCase = userQueryUseCase;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        // accessToken 없이 접근할 경우
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtTokenProvider.getSubject(accessToken);

        User user = userQueryUseCase.findByUsername(username);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,
            null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
