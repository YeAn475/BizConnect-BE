package com.springboot.bizconnect.domain.user.service;

import com.springboot.bizconnect.domain.user.dto.Sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.Sign.SignupResponseDto;

public interface UserService {
    SignupResponseDto addUser(SignupRequestDto requestDto);
}
