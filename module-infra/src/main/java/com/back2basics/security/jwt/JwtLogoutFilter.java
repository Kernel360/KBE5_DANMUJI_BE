package com.back2basics.security.jwt;

import static com.back2basics.global.response.code.AuthErrorCode.ALREADY_LOGOUT;
import static com.back2basics.global.response.code.AuthResponseCode.SUCCESS_LOGOUT;

import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.global.response.util.ResponseUtil;
import com.back2basics.security.exception.InvalidTokenException;
import com.back2basics.util.CookieUtil;
import com.back2basics.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    public JwtLogoutFilter(JwtTokenProvider jwtTokenProvider, RedisUtil redisUtil,
        CookieUtil cookieUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisUtil = redisUtil;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse,
            filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        if (!requestUri.equals("/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        try {
            jwtTokenProvider.validateAccessToken(accessToken);
        } catch (InvalidTokenException e) {
            ResponseUtil.writeJson(response, ApiResponse.error(e.getErrorCode(), e.getMessage()));
            return;
        }

        if (redisUtil.hasKey("BL:" + accessToken)) {
            ResponseEntity<ApiResponse<ErrorResponse>> apiResponse = ApiResponse.error(
                ALREADY_LOGOUT);
            ResponseUtil.writeJson(response, apiResponse);
            return;
        }

        long remainTime = jwtTokenProvider.getAccessTokenRemainingTime(accessToken);
        redisUtil.save("BL:" + accessToken, "logout", remainTime, TimeUnit.MILLISECONDS);
        redisUtil.delete("RT:" + jwtTokenProvider.getSubject(accessToken));

        cookieUtil.deleteRefreshTokenCookie(response);
        ResponseEntity<ApiResponse<Void>> apiResponse = ApiResponse.success(SUCCESS_LOGOUT);
        ResponseUtil.writeJson(response, apiResponse);
    }

}
