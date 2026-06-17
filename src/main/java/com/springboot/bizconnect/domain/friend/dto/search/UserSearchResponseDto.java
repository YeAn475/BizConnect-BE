package com.springboot.bizconnect.domain.friend.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchResponseDto {
    private Long userNo;
    private String name;
    private String email;
    private String companyName;
    private String imageUrl;
    private Boolean isOpen;
}