package com.springboot.bizconnect.domain.auth.service.impl;

import com.springboot.bizconnect.domain.auth.dto.login.LoginRequestDto;
import com.springboot.bizconnect.domain.auth.dto.login.LoginResponseDto;
import com.springboot.bizconnect.domain.auth.jwt.JwtTokenProvider;
import com.springboot.bizconnect.domain.auth.service.AuthService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().getName());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole().getName());

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
