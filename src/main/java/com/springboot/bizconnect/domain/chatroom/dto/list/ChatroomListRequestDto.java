package com.springboot.bizconnect.domain.chatroom.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatroomListRequestDto {
    private int page = 0;
    private int size = 20;
}
