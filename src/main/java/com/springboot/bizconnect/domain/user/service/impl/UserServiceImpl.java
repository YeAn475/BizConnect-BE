package com.springboot.bizconnect.domain.user.service.impl;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.auth.RefreshTokenService;
import com.springboot.bizconnect.domain.image.CloudinaryService;
import com.springboot.bizconnect.domain.user.dto.profile.UserProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.image.ProfileImageResponseDto;
import com.springboot.bizconnect.domain.user.dto.password.PasswordRequestDto;
import com.springboot.bizconnect.domain.user.dto.password.PasswordResponseDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileRequestDto;
import com.springboot.bizconnect.domain.user.dto.profile.ProfileResponseDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final UserStatusRepository userStatusRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final CloudinaryService cloudinaryService;

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
        User user = userRepository.findById(userDetails.getUser().getUserNo())
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
    public PasswordResponseDto updatePassword(CustomUserDetails userDetails, PasswordRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("회원정보가 없습니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) throw new RuntimeException("기존 비밀번호가 일치하지 않습니다.");

        if(!requestDto.getNewPassword().equals(requestDto.getConfirmPassword())) throw new RuntimeException("새 비밀번호가 일치하지 않습니다.");

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));

        userRepository.save(user);

        return PasswordResponseDto.builder()
                .message("비밀번호가 변경되었습니다.")
                .build();
    }

    @Override
    public String ChangePassword(CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("회원정보가 없습니다."));

        user.setIsOpen(!user.getIsOpen());

        userRepository.save(user);

        return user.getIsOpen() ? "프로필을 공개로 변경하였습니다." : "프로필을 비공개로 변경하였습니다.";
    }

    @Override
    public String deleteAccount(CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("회원정보가 없습니다."));

        user.setIsDeleted(true);

        userRepository.save(user);

        String email = user.getEmail();
        refreshTokenService.deleteRefreshToken(email);

        return "성공적으로 탈퇴하였습니다.";
    }

    @Override
    public ProfileImageResponseDto uploadProfileImage(CustomUserDetails userDetails, MultipartFile image) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 기존 이미지가 있으면 삭제
        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            cloudinaryService.delete(user.getImageUrl());
        }

        // 새 이미지 업로드
        String imageUrl = cloudinaryService.upload(image, "profile");

        // DB에 URL 저장
        user.setImageUrl(imageUrl);
        userRepository.save(user);

        return ProfileImageResponseDto.builder()
                .message("프로필 이미지가 업로드되었습니다.")
                .imageUrl(imageUrl)
                .build();
    }

    @Override
    public UserProfileResponseDto getProfile(CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));

        return UserProfileResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .companyName(user.getCompany().getName())
                .roleName(user.getRole().getName())
                .positionName(user.getPosition().getName())
                .userStatus(user.getUserStatus().getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .imageUrl(user.getImageUrl())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isOpened(user.getIsOpen())
                .build();
    }
}
