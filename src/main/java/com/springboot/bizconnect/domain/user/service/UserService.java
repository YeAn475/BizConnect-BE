package com.springboot.bizconnect.domain.user.service;

import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;

public interface UserService {
    SignupResponseDto signup(SignupRequestDto requestDto);
}
