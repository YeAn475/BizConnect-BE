package com.springboot.bizconnect.domain.user.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.user.dto.profile.UserProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.image.ProfileImageResponseDto;
import com.springboot.bizconnect.domain.user.dto.password.PasswordRequestDto;
import com.springboot.bizconnect.domain.user.dto.password.PasswordResponseDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    SignupResponseDto signup(SignupRequestDto requestDto);
    ProfileResponseDto updateProfile(CustomUserDetails userDetails, ProfileRequestDto requestDto);
    PasswordResponseDto updatePassword(CustomUserDetails userDetails, PasswordRequestDto requestDto);
    String ChangePassword(CustomUserDetails userDetails);
    String deleteAccount(CustomUserDetails userDetails);
    ProfileImageResponseDto uploadProfileImage(CustomUserDetails userDetails, MultipartFile image);
    UserProfileResponseDto getProfile(@AuthenticationPrincipal CustomUserDetails userDetails);
}
