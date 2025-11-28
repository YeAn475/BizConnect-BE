package com.springboot.bizconnect.domain.auth.service;

import com.springboot.bizconnect.domain.auth.dto.login.LoginRequestDto;
import com.springboot.bizconnect.domain.auth.dto.login.LoginResponseDto;


public interface AuthService {
    // 로그인
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    // 토큰 갱신
    LoginResponseDto refresh(String refreshToken);
    // 로그아웃
    void logout(String email);
}
