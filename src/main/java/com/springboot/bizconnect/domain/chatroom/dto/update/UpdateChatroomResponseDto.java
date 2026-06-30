package com.springboot.bizconnect.domain.chatroom.dto.update;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateChatroomResponseDto {
    private Long chatroomNo;
    private String name;
    private String message;
}
