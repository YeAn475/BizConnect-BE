package com.springboot.bizconnect.domain.chatroom.dto.create;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateChatroomResponseDto {
    private Long chatroomNo;
    private String name;
    private String createdByName;
    private LocalDateTime createdAt;
    private String message;
}
