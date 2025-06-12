package com.back2basics.domain.auth.controller;

import static com.back2basics.security.code.AuthResponseCode.SUCCESS_LOGIN;
import static com.back2basics.security.code.AuthResponseCode.SUCCESS_REISSUE;

import com.back2basics.domain.auth.AuthService;
import com.back2basics.domain.auth.dto.LoginRequest;
import com.back2basics.domain.auth.dto.TokenPair;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.code.AuthResponseCode;
import com.back2basics.security.jwt.JwtTokenProvider;
import com.back2basics.user.port.in.UpdateUserUseCase;
import com.back2basics.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CookieUtil cookieUtil;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
        HttpServletResponse response) {
        TokenPair tokenPair = authService.login(request);

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Authorization", "Bearer " + tokenPair.accessToken());

        response.addCookie(cookieUtil.createRefreshToken(tokenPair.refreshToken()));
        response.addCookie(cookieUtil.createAccessToken(tokenPair.accessToken()));

        updateUserUseCase.updateLastLoginAt(request.username());
        return ApiResponse.success(SUCCESS_LOGIN, tokenPair.accessToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        @CookieValue(value = "refreshToken", required = false) String refreshToken,
        HttpServletRequest request, HttpServletResponse response) {

        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        authService.logout(accessToken);

        response.addCookie(cookieUtil.deleteRefreshToken());
        response.addCookie(cookieUtil.deleteAccessToken());

        return ApiResponse.success(AuthResponseCode.SUCCESS_LOGOUT);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(
        HttpServletRequest request, HttpServletResponse response,
        @CookieValue(name = "refreshToken") String refreshToken) throws IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        TokenPair tokenPair = authService.reissue(refreshToken, accessToken);

        response.setHeader("Authorization", "Bearer " + tokenPair.accessToken());

        // 기존 쿠키 삭제
        response.addCookie(cookieUtil.deleteRefreshToken());
        response.addCookie(cookieUtil.deleteAccessToken());

        // 새로운 쿠키 생성
        response.addCookie(cookieUtil.createRefreshToken(tokenPair.refreshToken()));
        response.addCookie(cookieUtil.createAccessToken(tokenPair.accessToken()));
        return ApiResponse.success(SUCCESS_REISSUE, tokenPair.accessToken());
    }
}
