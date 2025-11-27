package com.springboot.bizconnect.domain.auth.service;

import com.springboot.bizconnect.domain.auth.dto.login.LoginRequestDto;
import com.springboot.bizconnect.domain.auth.dto.login.LoginResponseDto;


public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);

}
