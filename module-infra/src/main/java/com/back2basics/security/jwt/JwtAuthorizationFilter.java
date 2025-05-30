package com.back2basics.security.jwt;

import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_BLACKLISTED;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_INVALID;

import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.global.response.util.ResponseUtil;
import com.back2basics.security.exception.InvalidTokenException;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.util.CookieUtil;
import com.back2basics.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserQueryUseCase userQueryUseCase;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider,
        UserQueryUseCase userQueryUseCase, RedisUtil redisUtil, CookieUtil cookieUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userQueryUseCase = userQueryUseCase;
        this.redisUtil = redisUtil;
        this.cookieUtil = cookieUtil;
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

        if (redisUtil.hasKey(redisUtil.buildBlacklistTokenKey(accessToken))) {
            ResponseEntity<ApiResponse<ErrorResponse>> apiResponse = ApiResponse.error(
                TOKEN_BLACKLISTED);
            ResponseUtil.writeJson(response, apiResponse);
            return;
        }

        try {
            jwtTokenProvider.validateAccessToken(accessToken);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            log.error("InvalidTokenException: {}", e.getMessage());
            ResponseEntity<ApiResponse<ErrorResponse>> apiResponse = ApiResponse.error(
                TOKEN_INVALID);
            ResponseUtil.writeJson(response, apiResponse);
        }

    }

    private Authentication getAuthentication(String token) {
        String username = jwtTokenProvider.getSubject(token);
        User user = userQueryUseCase.findByUsername(username);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

}
