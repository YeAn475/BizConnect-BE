package com.springboot.bizconnect.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenSeravice {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 7; // 7일

    // refresh 토큰 저장
    public void saveRefreshToken(String email, String refreshToken) {
        String key = "refresh_token_" + email;
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    // refresh 토큰 조회
    public String getRefreshToken(String email) {
        String key = "refresh_token_" + email;
        return redisTemplate.opsForValue().get(key);
    }

    // refresh 토큰 삭제(로그아웃 시)
    public void deleteRefreshToken(String email) {
        String key = "refresh_token_" + email;
        redisTemplate.delete(key);
    }

    // refresh 토큰 유효한지 확인
    public boolean validateRefreshToken(String email, String refreshToken) {
        String savedToken = getRefreshToken(email);
        return savedToken != null && savedToken.equals(refreshToken);
    }
}
