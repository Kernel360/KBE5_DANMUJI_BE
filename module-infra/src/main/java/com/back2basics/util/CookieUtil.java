package com.back2basics.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    // 쿠키
    public Cookie createRefreshToken(String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public Cookie createAccessToken(String token) {
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);   // JS 접근 방지
        cookie.setSecure(true);     // HTTPS만
        cookie.setPath("/");
        cookie.setMaxAge(60 * 30);  // 30분

        return cookie;
    }

    public Cookie deleteRefreshToken() {
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        return deleteCookie;
    }

    public Cookie deleteAccessToken() {
        Cookie deleteCookie = new Cookie("accessToken", null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        return deleteCookie;
    }
}

