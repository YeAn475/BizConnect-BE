package com.springboot.bizconnect.domain.chat.dto.history;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageHistoryResponseDto {
    private Long messageNo;
    private Long userNo;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}
