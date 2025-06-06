package com.back2basics.global.interceptor;

import com.back2basics.global.utils.ClientUtils;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class IpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String ip = ClientUtils.getRemoteIp(request);

        Object principal = request.getUserPrincipal();
        // IP는 요청마다 바뀔 수 있으므로 요청이 들어올 때마다 CustomUserDetails에 IP를 덮어써야함
        if (principal instanceof CustomUserDetails customUserDetails) {
            customUserDetails.setIp(ip);
        }

        log.info("ip: {}", ip);
        return true;
    }
}
