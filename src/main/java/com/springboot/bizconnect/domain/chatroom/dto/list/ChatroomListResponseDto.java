package com.springboot.bizconnect.domain.chatroom.dto.list;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatroomListResponseDto {
    private Long chatroomNo;
    private String name;
    private String createdByName;
    private String status;
    private LocalDateTime createdAt;
    private boolean isJoined;
}
