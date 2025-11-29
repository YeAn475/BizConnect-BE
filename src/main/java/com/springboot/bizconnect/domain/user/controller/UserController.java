package com.springboot.bizconnect.domain.user.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.user.dto.password.PasswordRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface UserController {
    // 회원가입
    ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto);
    // 프로필 변경 (나중에 프로필 필요성 느끼면 추가)

    // 개인 정보 수정
    ResponseEntity<ProfileResponseDto> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ProfileRequestDto requestDto);
    // 비밀번호 수정
    ResponseEntity<PasswordRequestDto> updatePassword(@RequestBody @Valid PasswordRequestDto requestDto);
    // 프로필 공개 여부
    ResponseEntity<String> changeProfileOpen();
}
