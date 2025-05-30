package com.back2basics.security.auth;

import static com.back2basics.global.response.code.AuthResponseCode.SUCCESS_REISSUE;

import com.back2basics.global.response.result.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<String>> reissue(
        HttpServletRequest request, HttpServletResponse response,
        @CookieValue(name = "refreshToken") String refreshToken) throws IOException {
        String newAccessToken = authService.reissue(request, response, refreshToken);

        return ApiResponse.success(SUCCESS_REISSUE, newAccessToken);
    }
}
