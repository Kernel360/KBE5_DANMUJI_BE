package com.back2basics.global.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientUtils {

    // todo : 분기문보다는 반복문으로 리팩토링 가능할듯? 근데 순서가 중요한 것 같아서 일단 분기문으로 작성
    public static String getRemoteIp(HttpServletRequest request) {
        // 일반적인 프록시 환경 (Cloudflare, Nginx 등) - 여러 프록시를 거친 경우에도 첫 번째 IP가 원본 IP
        String ip = request.getHeader("x-forwarded-for"); // ex: 203.0.113.1, 10.0.0.1, 127.0.0.1
        log.debug("[ClientUtils] X-Forwarded-For: {}", ip);

        // nginx 등에서 사용하는 실 IP
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            log.debug("[ClientUtils] X-Real-IP: {}", ip);
        }

        // nginx 에서 간혹 사용하는 대체 표현
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
            log.debug("[ClientUtils] X-RealIP: {}", ip);
        }

        // proxy 환경일 경우 (구형 프록시?)
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("[ClientUtils] Proxy-Client-IP: {}", ip);

        }

        // 웹로직 서버일 경우(WAS)
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("[ClientUtils] WL-Proxy-Client-IP: {}", ip);
        }

        // ISP 프록시 환경 등 일부 환경(구형?)
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.debug("[ClientUtils] HTTP_CLIENT_IP: {}", ip);
        }

        // X-Forwarded-For의 오래된 명명 방식 (호환용)
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.debug("[ClientUtils] HTTP_X_FORWARDED_FOR: {}", ip);
        }

        // 위 모든 헤더가 아닌 곳에서 오는 ip
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.debug("[ClientUtils] request.getRemoteAddr(): {}", ip);
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

        log.debug("[ClientUtils] 사용자 IP: {}", ip);
        return ip;
    }
}
