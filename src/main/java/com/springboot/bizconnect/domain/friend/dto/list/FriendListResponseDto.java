package com.springboot.bizconnect.domain.friend.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendListResponseDto {
    private Long userNo;
    private String name;
    private String companyName;
    private String imageUrl;
    private LocalDateTime friendSince;
}