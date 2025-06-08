package com.back2basics.global.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientUtils {

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        log.debug("[ClientUtils] X-Forwarded-For: {}", ip);

        // proxy 환경일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("[ClientUtils] Proxy-Client-IP: {}", ip);

        }

        // 웹로직 서버일 경우(WAS)
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("[ClientUtils] WL-Proxy-Client-IP: {}", ip);

        }

        // 그 외
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
            log.debug("[ClientUtils] 다중 IP 분리 후 사용: {}", ip);
        }

        // 여러 ip가 있으면 첫 번째 꺼만 사용
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        // IPv6 형식 중 IPv4-mapped address (::ffff:192.0.2.128) 제거
        if (ip != null && ip.startsWith("::ffff:")) {
            ip = ip.substring(7);
            log.debug("[ClientUtils] IPv4-mapped IPv6 변환: {}", ip);
        }

        log.debug("[ClientUtils] 최종 반환 IP: {}", ip);
        return ip;
    }
}
