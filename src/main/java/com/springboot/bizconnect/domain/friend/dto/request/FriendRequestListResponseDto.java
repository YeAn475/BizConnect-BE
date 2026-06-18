package com.springboot.bizconnect.domain.friend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestListResponseDto {
    private Long requestNo;
    private Long userNo;
    private String name;
    private String companyName;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;
}