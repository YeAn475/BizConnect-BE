package com.springboot.bizconnect.domain.chat.dto.history;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageHistoryRequestDto {
    private int page = 0;
    private int size = 50;
}
