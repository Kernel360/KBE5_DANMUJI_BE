package com.back2basics.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    // 쿠키
    public Cookie create(String value) {
        Cookie cookie = new Cookie("refreshToken", value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public Cookie deleteRefreshToken() {
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        return deleteCookie;
    }
}

