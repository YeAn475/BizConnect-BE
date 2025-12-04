package com.springboot.bizconnect.domain.user.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileImageResponseDto {
    private String message;
    private String imageUrl;
}
