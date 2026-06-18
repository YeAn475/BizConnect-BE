package com.springboot.bizconnect.domain.friend.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendDetailResponseDto {
    private Long userNo;
    private String name;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String imageUrl;
    private LocalDateTime friendSince;
    private String message;  // 비공개 프로필일 경우 메시지
}