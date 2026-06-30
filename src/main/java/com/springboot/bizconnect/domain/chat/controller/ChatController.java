package com.springboot.bizconnect.domain.chat.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.chat.dto.history.MessageHistoryRequestDto;
import com.springboot.bizconnect.domain.chat.dto.history.MessageHistoryResponseDto;
import com.springboot.bizconnect.domain.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "채팅 메시지 관련 API")
public class ChatController {

    private final ChatService chatService;

    // 채팅방 메시지 이력 조회
    @GetMapping("/{chatroomNo}/messages")
    @Operation(summary = "메시지 이력 조회", description = "채팅방의 메시지 이력을 조회합니다. 참여자만 가능합니다.")
    public ResponseEntity<List<MessageHistoryResponseDto>> getMessageHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long chatroomNo,
            @ParameterObject MessageHistoryRequestDto requestDto
    ) {
        return ResponseEntity.ok(chatService.getMessageHistory(userDetails, chatroomNo, requestDto));
    }
}
