package com.shop31cm.config.jwt;

import com.shop31cm.enums.TokenType;
import com.shop31cm.exception.ErrorCode;
import com.shop31cm.exception.Shop31Exception;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    public String generateToken(String email, TokenType type) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + type.getExpiredTime()), email);
    }

    private String makeToken(Date expiry, String email) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(now)
            .setExpiration(expiry)
            .setSubject(email)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException |
            MalformedJwtException | SecurityException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        // 토큰 복호
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        // UserDetails 객체를 만들어서 Authentication 리턴
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
    }

    public String resolveToken(String authorization) {
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    public String reissueAccessToken(String refreshToken) throws Exception {
        if (!validToken(refreshToken)) {
            throw new Shop31Exception(ErrorCode.PASSWORD_NOT_FOUND);
        }

        String email = getEmailFromToken(refreshToken);
        String storedRefreshToken = (String) redisTemplate.opsForValue().get("refresh:" + email);

        if (!refreshToken.equals(storedRefreshToken)) {
            throw new Shop31Exception(ErrorCode.ALREADY_EXISTS_PHONE);
        }

        String tokenType = TokenType.ACCESS.toString().toLowerCase();
        String tokenKey = tokenType + ":" + email;
        deleteStoredToken(tokenType);

        // 새로운 Access Token 생성
        return generateNewAccessToken(email, tokenKey);

    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public void deleteStoredToken(String tokenKey) {
        // 기존에 redis에 있는 accessToken 무효화
        redisTemplate.delete(tokenKey);
    }

    private String generateNewAccessToken(String email, String tokenKey) {
        String newAccessToken = generateToken(email, TokenType.ACCESS);
        redisTemplate.opsForValue().set(tokenKey, newAccessToken, 6 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

        return newAccessToken;
    }

}
