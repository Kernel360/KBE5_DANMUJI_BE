package com.back2basics.security.jwt;

import static com.back2basics.global.response.code.AuthErrorCode.ACCESS_DENIED;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_MALFORMED;
import static com.back2basics.global.response.code.AuthResponseCode.SUCCESS_LOGIN;

import com.back2basics.auth.LoginRequest;
import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.global.response.util.ResponseUtil;
import com.back2basics.security.exception.CustomBadCredentialsException;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.util.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtil cookieUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider, CookieUtil cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new CustomBadCredentialsException(TOKEN_MALFORMED);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authentication)
        throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String access = jwtTokenProvider.createAccessToken(customUserDetails);
        String refresh = jwtTokenProvider.createRefreshToken(customUserDetails);
        response.setHeader("Authorization", "Bearer " + access);
        response.addCookie(cookieUtil.createCookie(refresh));

        ResponseEntity<ApiResponse<Void>> apiResponse = ApiResponse.success(SUCCESS_LOGIN);
        ResponseUtil.writeJson(response, apiResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) throws IOException {
        ResponseEntity<ApiResponse<ErrorResponse>> apiResponse = ApiResponse.error(ACCESS_DENIED);
        ResponseUtil.writeJson(response, apiResponse);
    }
}
