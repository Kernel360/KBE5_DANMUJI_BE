package com.back2basics.global.security.exception;

import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.code.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        ErrorCode errorCode = AuthErrorCode.AUTH_REQUIRED;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorCode, errorResponse).getBody();

        log.error("로그인 안해서 나오는 에러임 - AuthenticationEntryPoint {}", authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
