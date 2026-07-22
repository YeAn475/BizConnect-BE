package com.springboot.bizconnect.domain.chatroom.service;

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

import java.util.List;

public interface ChatroomService {

    CreateChatroomResponseDto createChatroom(CustomUserDetails userDetails, CreateChatroomRequestDto requestDto);

    UpdateChatroomResponseDto updateChatroom(CustomUserDetails userDetails, Long chatroomNo, UpdateChatroomRequestDto requestDto);

    DeleteChatroomResponseDto deleteChatroom(CustomUserDetails userDetails, Long chatroomNo);

    JoinChatroomResponseDto joinChatroom(CustomUserDetails userDetails, Long chatroomNo);

    LeaveChatroomResponseDto leaveChatroom(CustomUserDetails userDetails, Long chatroomNo);

    List<ChatroomListResponseDto> getAllChatrooms(CustomUserDetails userDetails, ChatroomListRequestDto requestDto);

    List<ChatroomListResponseDto> getJoinedChatrooms(CustomUserDetails userDetails, ChatroomListRequestDto requestDto);

    List<ChatroomListResponseDto> getMyCreatedChatrooms(CustomUserDetails userDetails, ChatroomListRequestDto requestDto);
}
