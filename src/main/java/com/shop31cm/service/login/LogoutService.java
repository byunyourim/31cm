package com.shop31cm.service.login;

import com.shop31cm.config.jwt.JwtTokenProvider;
import com.shop31cm.exception.ErrorCode;
import com.shop31cm.exception.Shop31Exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Shop31Exception(ErrorCode.INVALID_AUTHORIZATION);
        }

        String token =  jwtTokenProvider.resolveToken(request.getHeader("Authorization"));
        if (token != null && jwtTokenProvider.validToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);

            jwtTokenProvider.deleteStoredToken("access:" + email);
            jwtTokenProvider.deleteStoredToken("refresh:" + email);
        }
    }
}
