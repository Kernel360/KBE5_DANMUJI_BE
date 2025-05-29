package com.back2basics.security.jwt;

import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_INVALID;
import static com.back2basics.global.response.code.AuthResponseCode.SUCCESS_LOGOUT;

import com.back2basics.global.response.error.ErrorResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.global.response.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtLogoutFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
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
        if ((accessToken == null) || !jwtTokenProvider.validateAccessToken(accessToken)) {
            ResponseEntity<ApiResponse<ErrorResponse>> apiResponse = ApiResponse.error(
                TOKEN_INVALID);
            ResponseUtil.writeJson(response, apiResponse);
            return;
        }

        ResponseEntity<ApiResponse<Void>> apiResponse = ApiResponse.success(SUCCESS_LOGOUT);
        ResponseUtil.writeJson(response, apiResponse);
    }

}
