package com.back2basics.global.security.filter;

import com.back2basics.global.utils.ClientUtils;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class IpInjectionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
            && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            String ip = ClientUtils.getRemoteIp(request);
            userDetails.setIp(ip);
            log.debug("========= CustomUserDetails에 IP 주입 : {}", ip);
        }

        filterChain.doFilter(request, response);
    }


}