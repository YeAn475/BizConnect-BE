package com.springboot.bizconnect.domain.chatroom.service.impl;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.chatroom.dto.create.CreateChatroomRequestDto;
import com.springboot.bizconnect.domain.chatroom.dto.create.CreateChatroomResponseDto;
import com.springboot.bizconnect.domain.chatroom.dto.delete.DeleteChatroomResponseDto;
import com.springboot.bizconnect.domain.chatroom.dto.join.JoinChatroomResponseDto;
import com.springboot.bizconnect.domain.chatroom.dto.leave.LeaveChatroomResponseDto;
import com.springboot.bizconnect.domain.chatroom.dto.list.ChatroomListRequestDto;
import com.springboot.bizconnect.domain.chatroom.dto.list.ChatroomListResponseDto;
import com.springboot.bizconnect.domain.chatroom.dto.update.UpdateChatroomRequestDto;
import com.springboot.bizconnect.domain.chatroom.dto.update.UpdateChatroomResponseDto;
import com.springboot.bizconnect.domain.chatroom.repository.ChatJoinRepository;
import com.springboot.bizconnect.domain.chatroom.repository.ChatroomRepository;
import com.springboot.bizconnect.domain.chatroom.service.ChatroomService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.ChatJoin;
import com.springboot.bizconnect.entity.Chatroom;
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
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final UserRepository userRepository;

    // ==================== 채팅방 생성 ====================

    @Override
    public CreateChatroomResponseDto createChatroom(CustomUserDetails userDetails, CreateChatroomRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Chatroom chatroom = Chatroom.builder()
                .name(requestDto.getName())
                .createdBy(user)
                .status(chatroomStatus.ACTIVE)
                .build();

        chatroomRepository.save(chatroom);

        // 생성자는 자동으로 채팅방에 입장
        ChatJoin chatJoin = ChatJoin.builder()
                .no(new ChatJoin.chatJoinNo(user.getUserNo(), chatroom.getChatroomNo()))
                .user(user)
                .chatroom(chatroom)
                .build();

        chatJoinRepository.save(chatJoin);

        return CreateChatroomResponseDto.builder()
                .chatroomNo(chatroom.getChatroomNo())
                .name(chatroom.getName())
                .createdByName(user.getName())
                .createdAt(chatroom.getCreatedAt())
                .message("채팅방이 생성되었습니다.")
                .build();
    }

    // ==================== 채팅방 수정 ====================

    @Override
    public UpdateChatroomResponseDto updateChatroom(CustomUserDetails userDetails, Long chatroomNo, UpdateChatroomRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();

        Chatroom chatroom = chatroomRepository.findById(chatroomNo)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        if (!chatroom.getCreatedBy().getUserNo().equals(userNo)) {
            throw new RuntimeException("채팅방 수정 권한이 없습니다.");
        }

        if (chatroom.getStatus() == chatroomStatus.DELETED) {
            throw new RuntimeException("삭제된 채팅방입니다.");
        }

        chatroom.setName(requestDto.getName());
        chatroomRepository.save(chatroom);

        return UpdateChatroomResponseDto.builder()
                .chatroomNo(chatroom.getChatroomNo())
                .name(chatroom.getName())
                .message("채팅방이 수정되었습니다.")
                .build();
    }

    // ==================== 채팅방 삭제 ====================

    @Override
    public DeleteChatroomResponseDto deleteChatroom(CustomUserDetails userDetails, Long chatroomNo) {
        Long userNo = userDetails.getUser().getUserNo();

        Chatroom chatroom = chatroomRepository.findById(chatroomNo)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        if (!chatroom.getCreatedBy().getUserNo().equals(userNo)) {
            throw new RuntimeException("채팅방 삭제 권한이 없습니다.");
        }

        chatroom.setStatus(chatroomStatus.DELETED);
        chatroomRepository.save(chatroom);

        return DeleteChatroomResponseDto.builder()
                .message("채팅방이 삭제되었습니다.")
                .build();
    }

    // ==================== 채팅방 입장 ====================

    @Override
    public JoinChatroomResponseDto joinChatroom(CustomUserDetails userDetails, Long chatroomNo) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Chatroom chatroom = chatroomRepository.findById(chatroomNo)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        if (chatroom.getStatus() == chatroomStatus.DELETED) {
            throw new RuntimeException("삭제된 채팅방입니다.");
        }

        if (chatJoinRepository.existsByUserAndChatroom(user, chatroom)) {
            throw new RuntimeException("이미 참여 중인 채팅방입니다.");
        }

        ChatJoin chatJoin = ChatJoin.builder()
                .no(new ChatJoin.chatJoinNo(user.getUserNo(), chatroom.getChatroomNo()))
                .user(user)
                .chatroom(chatroom)
                .build();

        chatJoinRepository.save(chatJoin);

        return JoinChatroomResponseDto.builder()
                .message("채팅방에 입장했습니다.")
                .build();
    }

    // ==================== 채팅방 나가기 ====================

    @Override
    public LeaveChatroomResponseDto leaveChatroom(CustomUserDetails userDetails, Long chatroomNo) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Chatroom chatroom = chatroomRepository.findById(chatroomNo)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        ChatJoin chatJoin = chatJoinRepository.findByUserAndChatroom(user, chatroom)
                .orElseThrow(() -> new RuntimeException("참여 중인 채팅방이 아닙니다."));

        chatJoinRepository.delete(chatJoin);

        return LeaveChatroomResponseDto.builder()
                .message("채팅방에서 나갔습니다.")
                .build();
    }

    // ==================== 채팅방 목록 ====================

    @Override
    public List<ChatroomListResponseDto> getAllChatrooms(CustomUserDetails userDetails, ChatroomListRequestDto requestDto) {
        User user = userDetails.getUser();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return chatroomRepository.findByStatusNot(chatroomStatus.DELETED, pageRequest)
                .map(chatroom -> ChatroomListResponseDto.builder()
                        .chatroomNo(chatroom.getChatroomNo())
                        .name(chatroom.getName())
                        .createdByName(chatroom.getCreatedBy().getName())
                        .status(chatroom.getStatus().name())
                        .createdAt(chatroom.getCreatedAt())
                        .isJoined(chatJoinRepository.existsByUserAndChatroom(user, chatroom))
                        .build())
                .getContent();
    }

    @Override
    public List<ChatroomListResponseDto> getJoinedChatrooms(CustomUserDetails userDetails, ChatroomListRequestDto requestDto) {
        User user = userDetails.getUser();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return chatJoinRepository.findByUser(user, pageRequest)
                .map(chatJoin -> {
                    Chatroom chatroom = chatJoin.getChatroom();
                    return ChatroomListResponseDto.builder()
                            .chatroomNo(chatroom.getChatroomNo())
                            .name(chatroom.getName())
                            .createdByName(chatroom.getCreatedBy().getName())
                            .status(chatroom.getStatus().name())
                            .createdAt(chatroom.getCreatedAt())
                            .isJoined(true)
                            .build();
                })
                .getContent();
    }

    @Override
    public List<ChatroomListResponseDto> getMyCreatedChatrooms(CustomUserDetails userDetails, ChatroomListRequestDto requestDto) {
        User user = userDetails.getUser();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return chatroomRepository.findByCreatedByAndStatusNot(user, chatroomStatus.DELETED, pageRequest)
                .map(chatroom -> ChatroomListResponseDto.builder()
                        .chatroomNo(chatroom.getChatroomNo())
                        .name(chatroom.getName())
                        .createdByName(chatroom.getCreatedBy().getName())
                        .status(chatroom.getStatus().name())
                        .createdAt(chatroom.getCreatedAt())
                        .isJoined(true)
                        .build())
                .getContent();
    }
}
