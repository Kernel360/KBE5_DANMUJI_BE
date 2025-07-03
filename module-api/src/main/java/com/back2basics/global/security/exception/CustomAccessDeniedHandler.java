package com.back2basics.global.security.exception;

import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.code.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
        throws IOException {

        log.error("권한 없어서 나오는 에러임 - Access Denied {}", accessDeniedException.getMessage());

        AuthErrorCode errorCode = AuthErrorCode.ACCESS_DENIED;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorCode, errorResponse).getBody();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}

