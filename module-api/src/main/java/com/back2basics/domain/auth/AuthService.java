package com.back2basics.domain.auth;

import static com.back2basics.security.code.AuthErrorCode.ALREADY_LOGOUT;
import static com.back2basics.security.code.AuthErrorCode.TOKEN_NOT_FOUND;

import com.back2basics.domain.auth.dto.LoginRequest;
import com.back2basics.domain.auth.dto.TokenPair;
import com.back2basics.security.exception.InvalidTokenException;
import com.back2basics.security.jwt.JwtTokenProvider;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.security.service.CustomUserDetailsService;
import com.back2basics.util.CookieUtil;
import com.back2basics.util.RedisUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    public TokenPair login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String access = jwtTokenProvider.createAccessToken(userDetails);
        String refresh = jwtTokenProvider.createRefreshToken(userDetails);

        return new TokenPair(access, refresh);
    }

    public void logout(String accessToken) {
        // 1. 토큰 유효성 검증
        jwtTokenProvider.validateAccessToken(accessToken);

        // 2. 이미 로그아웃한 토큰인지 확인
        String blacklistKey = redisUtil.buildBlacklistTokenKey(accessToken);
        if (redisUtil.hasKey(blacklistKey)) {
            throw new InvalidTokenException(ALREADY_LOGOUT);
        }

        // 3. 블랙리스트 등록 (남은 만료 시간 기준)
        String username = jwtTokenProvider.getSubjectIgnoringExpiration(accessToken);
        long remainTime = jwtTokenProvider.getAccessTokenRemainingTime(accessToken);
        redisUtil.save(blacklistKey, "logout", remainTime, TimeUnit.MILLISECONDS);

        // 4. refreshToken 제거
        redisUtil.delete(redisUtil.buildRefreshTokenKey(username));

    }

    public TokenPair reissue(String refreshToken, String accessToken) throws IOException {

        // 1. Refresh Token 검증
        jwtTokenProvider.validateRefreshToken(refreshToken);

        if (!jwtTokenProvider.isRefreshTokenValidInRedis(refreshToken)) {
            throw new InvalidTokenException(TOKEN_NOT_FOUND);
        }

        // 2. 사용자 ID 추출
        String username = jwtTokenProvider.getSubject(refreshToken);
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);

        // 3. 기존 Access Token을 블랙리스트에 추가
        long remainTime = jwtTokenProvider.getAccessTokenRemainingTime(accessToken);
        redisUtil.save(redisUtil.buildBlacklistTokenKey(accessToken), "logout",
            remainTime, TimeUnit.MILLISECONDS);
        redisUtil.delete(redisUtil.buildRefreshTokenKey(username));

        // 4. 새로운 Access Token과 Refresh Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(
            (CustomUserDetails) customUserDetails);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(
            (CustomUserDetails) customUserDetails);

        return new TokenPair(newAccessToken, newRefreshToken);
    }
}
