package com.back2basics.interceptor;

import com.back2basics.global.utils.ClientUtils;
import com.back2basics.global.utils.IpHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class IpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull  Object handler) {

        String ip = ClientUtils.getRemoteIp(request);
        IpHolder.set(ip);
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        IpHolder.clear(); // 메모리 누수 방지 -> 요청 완료 후 IP 제거
    }
}