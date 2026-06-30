package com.springboot.bizconnect.domain.chat.dto.send;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SendMessageResponseDto {
    private Long messageNo;
    private Long chatroomNo;
    private Long userNo;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}
