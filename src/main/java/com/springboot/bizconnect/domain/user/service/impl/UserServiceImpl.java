package com.springboot.bizconnect.domain.user.service.impl;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.user.dto.password.PasswordRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;
import com.springboot.bizconnect.domain.user.repository.CompanyRepository;
import com.springboot.bizconnect.domain.user.repository.PositionRepository;
import com.springboot.bizconnect.domain.user.repository.RoleRepository;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.domain.user.repository.UserStatusRepository;
import com.springboot.bizconnect.domain.user.service.UserService;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.Position;
import com.springboot.bizconnect.entity.Role;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.entity.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final UserStatusRepository userStatusRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) throw new RuntimeException("이미 존재하는 이메일입니다.");

        if(!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        Role defaultRole = roleRepository.findById(1L).
                orElseThrow(() -> new RuntimeException("Role을 찾을 수 없습니다."));
        Position defaultPosition = positionRepository.findById(1L).
                orElseThrow(() -> new RuntimeException("Position을 찾을 수 없습니다."));
        UserStatus defaultUserStatus = userStatusRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("UserStatus를 찾을 수 없습니다."));
        Company defaultCompany = companyRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Company를 찾을 수 없습니다."));


        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .phoneNumber(requestDto.getPhoneNumber())
                .address(requestDto.getAddress())
                .role(defaultRole)
                .position(defaultPosition)
                .userStatus(defaultUserStatus)
                .company(defaultCompany)
                .build();

        User savedUser = userRepository.save(user);

        return SignupResponseDto.builder()
                .message("회원가입을 성공했습니다.")
                .build();

    }

    @Override
    public ProfileResponseDto updateProfile(CustomUserDetails userDetails, ProfileRequestDto requestDto) {
        Long userNo = Long.valueOf(userDetails.getUser().getUserNo());

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        if(requestDto.getPhoneNumber() != null) user.setPhoneNumber(requestDto.getPhoneNumber());
        if(requestDto.getAddress() != null) user.setAddress(requestDto.getAddress());

        User savedUser = userRepository.save(user);

        return ProfileResponseDto.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .address(savedUser.getAddress())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    @Override
    public PasswordRequestDto updatePassword(PasswordRequestDto requestDto) {
        return null;
    }

    @Override
    public String ChangePassword() {
        return "";
    }
}
