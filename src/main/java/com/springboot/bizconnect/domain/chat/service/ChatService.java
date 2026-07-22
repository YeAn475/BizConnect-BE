package com.springboot.bizconnect.domain.chat.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.chat.dto.history.MessageHistoryRequestDto;
import com.springboot.bizconnect.domain.chat.dto.history.MessageHistoryResponseDto;
import com.springboot.bizconnect.domain.chat.dto.send.SendMessageRequestDto;
import com.springboot.bizconnect.domain.chat.dto.send.SendMessageResponseDto;

import java.util.List;

public interface ChatService {

    SendMessageResponseDto sendMessage(CustomUserDetails userDetails, SendMessageRequestDto requestDto);

    List<MessageHistoryResponseDto> getMessageHistory(CustomUserDetails userDetails, Long chatroomNo, MessageHistoryRequestDto requestDto);
}
