package com.back2basics.security.auth;

import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_INVALID;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_NOT_FOUND;

import com.back2basics.security.exception.InvalidTokenException;
import com.back2basics.security.jwt.JwtTokenProvider;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.security.service.CustomUserDetailsService;
import com.back2basics.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CookieUtil cookieUtil;

    public String reissue(HttpServletRequest request, HttpServletResponse response,
        String refreshToken) throws IOException {

        // 1. Refresh Token 검증
        try {
            jwtTokenProvider.validateRefreshToken(refreshToken);
        } catch (InvalidTokenException e) {
            log.error("InvalidTokenException: {}", e.getMessage());
            response.sendError(TOKEN_INVALID.getStatus().value(), TOKEN_INVALID.getMessage());
            return null;
        }

        if (!jwtTokenProvider.hasRedisRefreshToken(refreshToken)) {
            response.sendError(TOKEN_NOT_FOUND.getStatus().value(), TOKEN_NOT_FOUND.getMessage());
            return null;
        }

        // 2. 사용자 ID 추출
        String username = jwtTokenProvider.getSubject(refreshToken);
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);

        // 3. 새로운 Access Token과 Refresh Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(
            (CustomUserDetails) customUserDetails);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(
            (CustomUserDetails) customUserDetails);

        // 4. 응답 헤더와 쿠키에 새로운 토큰 설정
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(cookieUtil.createCookie(newRefreshToken));

        return newAccessToken;
    }
}
