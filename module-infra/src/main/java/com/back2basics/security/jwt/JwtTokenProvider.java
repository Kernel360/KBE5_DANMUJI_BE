package com.back2basics.security.jwt;

import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_EXPIRED;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_INVALID;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_MALFORMED;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_SIGNATURE_INVALID;
import static com.back2basics.global.response.code.AuthErrorCode.TOKEN_UNSUPPORTED;

import com.back2basics.security.exception.InvalidTokenException;
import com.back2basics.security.model.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final Long accessTokenExpirationMs;
    private final Long refreshTokenExpirationMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
        @Value("${jwt.access}") Long accessTokenExpirationMs,
        @Value("${jwt.refresh}") Long refreshTokenExpirationMs) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String createToken(CustomUserDetails customUserDetails, Instant expiration) {
        Instant issuedAt = Instant.now();
        String authorities = customUserDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .and()
            .subject(customUserDetails.getUsername())
            .claim("id", customUserDetails.getId())
            .claim("role", authorities)
            .issuedAt(Date.from(issuedAt))
            .expiration(Date.from(expiration))
            .signWith(secretKey)
            .compact();
    }

    public String createAccessToken(CustomUserDetails userDetails) {
        return createToken(userDetails, Instant.now().plusMillis(accessTokenExpirationMs));
    }

    public String createRefreshToken(CustomUserDetails userDetails) {
        return createToken(userDetails, Instant.now().plusMillis(refreshTokenExpirationMs));
    }

    // HTTP 요청의 'Authorization' 헤더에서 JWT 액세스 토큰을 검색
    public String resolveAccessToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    // TOKEN에서 USERNAME 추출
    public String getSubject(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    // 액세스 토큰이 유효한지 확인
    public void validateAccessToken(String accessToken) {
        try {
            Jwts.parser()
                .clockSkewSeconds(180)
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException(TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException(TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException(TOKEN_MALFORMED);
        } catch (SecurityException | SignatureException e) {
            throw new InvalidTokenException(TOKEN_SIGNATURE_INVALID);
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException(TOKEN_INVALID);
        }
    }
}
