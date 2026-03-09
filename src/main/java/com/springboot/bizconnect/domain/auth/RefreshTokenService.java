package com.springboot.bizconnect.domain.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
// @RequiredArgsConstructor를 제거하고 생성자를 직접 작성합니다.
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    
    private static final String KEY_PREFIX = "RT:";
    private static final long REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 7; // 7일

    /**
     * 직접 생성자를 작성하여 @Qualifier를 주입합니다.
     * STS(Eclipse)에서 롬복이 파라미터 이름을 제대로 보존하지 못할 때 가장 확실한 해결 방법입니다.
     */
    public RefreshTokenService(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // refresh 토큰 저장
    public void saveRefreshToken(String email, String refreshToken) {
        String key = KEY_PREFIX + email;
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    // refresh 토큰 조회
    public String getRefreshToken(String email) {
        String key = KEY_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }

    // refresh 토큰 삭제 (로그아웃 시)
    public void deleteRefreshToken(String email) {
        String key = KEY_PREFIX + email;
        redisTemplate.delete(key);
    }

    // refresh 토큰 유효한지 확인
    public boolean validateRefreshToken(String email, String refreshToken) {
        String savedToken = getRefreshToken(email);
        return savedToken != null && savedToken.equals(refreshToken);
    }
}