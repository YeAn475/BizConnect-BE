package com.springboot.bizconnect.domain.user.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UserProfileResponseDto {
    private String name;
    private String roleName;
    private String companyName;
    private String positionName;
    private String userStatus;
    private String email;
    private String phoneNumber;
    private String address;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isOpened;
}
