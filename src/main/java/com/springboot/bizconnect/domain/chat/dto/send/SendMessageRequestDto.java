package com.springboot.bizconnect.domain.chat.dto.send;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMessageRequestDto {
    private Long chatroomNo;
    private String content;
}
