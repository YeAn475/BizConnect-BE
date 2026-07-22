package com.springboot.bizconnect.domain.chat.service.impl;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.chat.dto.history.MessageHistoryRequestDto;
import com.springboot.bizconnect.domain.chat.dto.history.MessageHistoryResponseDto;
import com.springboot.bizconnect.domain.chat.dto.send.SendMessageRequestDto;
import com.springboot.bizconnect.domain.chat.dto.send.SendMessageResponseDto;
import com.springboot.bizconnect.domain.chat.repository.MessageRepository;
import com.springboot.bizconnect.domain.chat.service.ChatService;
import com.springboot.bizconnect.domain.chatroom.repository.ChatJoinRepository;
import com.springboot.bizconnect.domain.chatroom.repository.ChatroomRepository;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.Chatroom;
import com.springboot.bizconnect.entity.Message;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.chatroomStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final UserRepository userRepository;

    // ==================== 메시지 전송 ====================

    @Override
    public SendMessageResponseDto sendMessage(CustomUserDetails userDetails, SendMessageRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Chatroom chatroom = chatroomRepository.findById(requestDto.getChatroomNo())
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        if (chatroom.getStatus() == chatroomStatus.DELETED) {
            throw new RuntimeException("삭제된 채팅방입니다.");
        }

        if (!chatJoinRepository.existsByUserAndChatroom(user, chatroom)) {
            throw new RuntimeException("채팅방에 참여 후 메시지를 보낼 수 있습니다.");
        }

        Message message = Message.builder()
                .user(user)
                .chatroom(chatroom)
                .content(requestDto.getContent())
                .build();

        messageRepository.save(message);

        // 마지막 읽은 메시지 갱신
        chatJoinRepository.findByUserAndChatroom(user, chatroom).ifPresent(join -> {
            join.setLastReadMessageNo(message.getMessageNo());
            chatJoinRepository.save(join);
        });

        return SendMessageResponseDto.builder()
                .messageNo(message.getMessageNo())
                .chatroomNo(chatroom.getChatroomNo())
                .userNo(user.getUserNo())
                .userName(user.getName())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    // ==================== 메시지 이력 조회 ====================

    @Override
    public List<MessageHistoryResponseDto> getMessageHistory(CustomUserDetails userDetails, Long chatroomNo, MessageHistoryRequestDto requestDto) {
        User user = userDetails.getUser();

        Chatroom chatroom = chatroomRepository.findById(chatroomNo)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        if (!chatJoinRepository.existsByUserAndChatroom(user, chatroom)) {
            throw new RuntimeException("참여 중인 채팅방이 아닙니다.");
        }

        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return messageRepository.findByChatroomOrderByCreatedAtAsc(chatroom, pageRequest)
                .map(message -> MessageHistoryResponseDto.builder()
                        .messageNo(message.getMessageNo())
                        .userNo(message.getUser().getUserNo())
                        .userName(message.getUser().getName())
                        .content(message.getContent())
                        .createdAt(message.getCreatedAt())
                        .build())
                .getContent();
    }
}
