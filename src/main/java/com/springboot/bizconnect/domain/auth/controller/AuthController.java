package com.springboot.bizconnect.domain.auth.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.auth.dto.login.LoginRequestDto;
import com.springboot.bizconnect.domain.auth.dto.login.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {
    // 로그인
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto);
    // Refresh 토큰 재발급
    ResponseEntity<LoginResponseDto> refresh(@RequestBody String refreshToken);
    // 로그아웃
    ResponseEntity<String> logout(@AuthenticationPrincipal CustomUserDetails userDetails);
}
