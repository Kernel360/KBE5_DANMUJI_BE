package com.back2basics.global.config;

import com.back2basics.security.jwt.JwtAuthenticationFilter;
import com.back2basics.security.jwt.JwtAuthorizationFilter;
import com.back2basics.security.jwt.JwtLogoutFilter;
import com.back2basics.security.jwt.JwtTokenProvider;
import com.back2basics.util.CookieUtil;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] allowedUrls = {"/", "/login", "/error"};

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtLogoutFilter jwtLogoutFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CookieUtil cookieUtil;

    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter,
        JwtLogoutFilter jwtLogoutFilter,
        JwtTokenProvider jwtTokenProvider,
        AuthenticationConfiguration authenticationConfiguration, CookieUtil cookieUtil) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtLogoutFilter = jwtLogoutFilter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationConfiguration = authenticationConfiguration;
        this.cookieUtil = cookieUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
            .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화 (JWT 사용 시 필요)
            .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
            .sessionManagement(
                session -> session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // 세션 비활성화
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(allowedUrls).permitAll()
                    .requestMatchers("/static/**", "/assets/**")
                    .permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 전용 API 보호
                    .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
//                    .anyRequest().permitAll() // todo
            )
            .addFilterAt(
                new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration),
                    jwtTokenProvider, cookieUtil), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtAuthorizationFilter, JwtAuthenticationFilter.class)
            .addFilterBefore(jwtLogoutFilter, LogoutFilter.class)
            .build();
    }

    // TODO: Frontend CORS 설정에 맞게 수정 필요
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // 인증 정보 포함 허용 (Cookies 등)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
