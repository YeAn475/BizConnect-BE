package com.springboot.bizconnect.domain.user.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.user.dto.password.PasswordRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;

public interface UserService {
    SignupResponseDto signup(SignupRequestDto requestDto);
    ProfileResponseDto updateProfile(CustomUserDetails userDetails, ProfileRequestDto requestDto);
    PasswordRequestDto updatePassword(PasswordRequestDto requestDto);
    String ChangePassword();
}
