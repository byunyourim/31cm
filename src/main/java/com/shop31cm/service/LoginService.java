package com.shop31cm.service;

import com.shop31cm.common.constants.Shop31Constant;
import com.shop31cm.config.jwt.JwtTokenProvider;
import com.shop31cm.domain.User;
import com.shop31cm.enums.TokenType;
import com.shop31cm.exception.ErrorCode;
import com.shop31cm.exception.Shop31Exception;
import com.shop31cm.service.login.dto.LoginDto;
import com.shop31cm.service.user.UserService;
import com.shop31cm.utils.PasswordUtils;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void login(LoginDto loginDto) {
        String email = loginDto.getUsername();
        String password = loginDto.getPassword();

        User user = userService.getUserByEmail(email);

        if (PasswordUtils.matches(password, user.getPassword())) {
            throw new Shop31Exception(ErrorCode.INCORRECT_PASSWORD);
        }

        String accessKey = Shop31Constant.ACCESS_LOCK + Shop31Constant.BASIC_DLIIMITER + email;
        String refreshKey = Shop31Constant.REFRESH_LOCK + Shop31Constant.BASIC_DLIIMITER + email;

        invalidatePreviousToken(accessKey, refreshKey);

        String accessToken = jwtTokenProvider.generateToken(email, TokenType.ACCESS);
        String refreshToken = jwtTokenProvider.generateToken(email, TokenType.REFRESH);

        log.info(accessToken);
        log.info(refreshToken);

        saveToken(accessKey, accessToken, 6 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);
        saveToken(refreshKey, refreshToken, 7, TimeUnit.DAYS);

    }
    private void saveToken(String key, String token, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, token, timeout, timeUnit);
    }

    private void invalidatePreviousToken(String accessKey, String refreshKey) {
        redisTemplate.delete(accessKey);
        redisTemplate.delete(refreshKey);
    }
}
