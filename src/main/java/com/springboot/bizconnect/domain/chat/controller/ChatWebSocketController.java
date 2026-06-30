package com.springboot.bizconnect.domain.chat.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.chat.dto.send.SendMessageRequestDto;
import com.springboot.bizconnect.domain.chat.dto.send.SendMessageResponseDto;
import com.springboot.bizconnect.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    /*
     * ────────────────────────────────────────────────────────
     * WebSocket 연결 방법 (STOMP)
     * ────────────────────────────────────────────────────────
     * 1. 연결 엔드포인트
     *    - SockJS: http://localhost:8301/ws
     *    - 순수 WebSocket: ws://localhost:8301/ws/websocket
     *
     * 2. CONNECT 헤더
     *    Authorization: Bearer {accessToken}
     *
     * 3. 메시지 전송 (클라이언트 → 서버)
     *    destination: /app/chat.send
     *    body: {"chatroomNo": 1, "content": "안녕하세요"}
     *
     * 4. 메시지 수신 구독 (서버 → 클라이언트)
     *    SUBSCRIBE /topic/chatroom/{chatroomNo}
     *
     * 5. 에러 수신 구독
     *    SUBSCRIBE /user/queue/errors
     * ────────────────────────────────────────────────────────
     */
    @MessageMapping("/chat.send")
    public void sendMessage(
            @Payload SendMessageRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            SendMessageResponseDto response = chatService.sendMessage(userDetails, requestDto);
            messagingTemplate.convertAndSend(
                    "/topic/chatroom/" + requestDto.getChatroomNo(),
                    response
            );
        } catch (Exception e) {
            log.error("메시지 전송 실패: {}", e.getMessage());
            messagingTemplate.convertAndSendToUser(
                    userDetails.getUsername(),
                    "/queue/errors",
                    e.getMessage()
            );
        }
    }
}
