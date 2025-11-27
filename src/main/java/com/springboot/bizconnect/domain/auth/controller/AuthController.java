package com.springboot.bizconnect.domain.auth.controller;

import com.springboot.bizconnect.domain.auth.dto.login.LoginRequestDto;
import com.springboot.bizconnect.domain.auth.dto.login.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {
    // 로그인
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto);

}
