package com.springboot.bizconnect.domain.chatroom.controller;

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
import com.springboot.bizconnect.domain.chatroom.service.ChatroomService;
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
@RequestMapping("/api/chatroom")
@Tag(name = "Chatroom", description = "채팅방 관련 API")
public class chatroomController {

    private final ChatroomService chatroomService;

    // 채팅방 생성
    @PostMapping
    @Operation(summary = "채팅방 생성", description = "새 채팅방을 생성합니다. 생성자는 자동으로 입장됩니다.")
    public ResponseEntity<CreateChatroomResponseDto> createChatroom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateChatroomRequestDto requestDto
    ) {
        return ResponseEntity.ok(chatroomService.createChatroom(userDetails, requestDto));
    }

    // 채팅방 수정
    @PutMapping("/{chatroomNo}")
    @Operation(summary = "채팅방 수정", description = "채팅방 이름을 수정합니다. 생성자만 가능합니다.")
    public ResponseEntity<UpdateChatroomResponseDto> updateChatroom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long chatroomNo,
            @RequestBody UpdateChatroomRequestDto requestDto
    ) {
        return ResponseEntity.ok(chatroomService.updateChatroom(userDetails, chatroomNo, requestDto));
    }

    // 채팅방 삭제
    @DeleteMapping("/{chatroomNo}")
    @Operation(summary = "채팅방 삭제", description = "채팅방을 삭제합니다. 생성자만 가능합니다.")
    public ResponseEntity<DeleteChatroomResponseDto> deleteChatroom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long chatroomNo
    ) {
        return ResponseEntity.ok(chatroomService.deleteChatroom(userDetails, chatroomNo));
    }

    // 채팅방 입장
    @PostMapping("/{chatroomNo}/join")
    @Operation(summary = "채팅방 입장", description = "채팅방에 참여합니다.")
    public ResponseEntity<JoinChatroomResponseDto> joinChatroom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long chatroomNo
    ) {
        return ResponseEntity.ok(chatroomService.joinChatroom(userDetails, chatroomNo));
    }

    // 채팅방 나가기
    @DeleteMapping("/{chatroomNo}/leave")
    @Operation(summary = "채팅방 나가기", description = "채팅방에서 나갑니다.")
    public ResponseEntity<LeaveChatroomResponseDto> leaveChatroom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long chatroomNo
    ) {
        return ResponseEntity.ok(chatroomService.leaveChatroom(userDetails, chatroomNo));
    }

    // 전체 채팅방 목록
    @GetMapping
    @Operation(summary = "전체 채팅방 목록", description = "삭제되지 않은 전체 채팅방 목록을 조회합니다.")
    public ResponseEntity<List<ChatroomListResponseDto>> getAllChatrooms(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject ChatroomListRequestDto requestDto
    ) {
        return ResponseEntity.ok(chatroomService.getAllChatrooms(userDetails, requestDto));
    }

    // 내가 참여한 채팅방 목록
    @GetMapping("/joined")
    @Operation(summary = "내가 참여한 채팅방 목록", description = "내가 참여 중인 채팅방 목록을 조회합니다.")
    public ResponseEntity<List<ChatroomListResponseDto>> getJoinedChatrooms(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject ChatroomListRequestDto requestDto
    ) {
        return ResponseEntity.ok(chatroomService.getJoinedChatrooms(userDetails, requestDto));
    }

    // 내가 생성한 채팅방 목록
    @GetMapping("/created")
    @Operation(summary = "내가 생성한 채팅방 목록", description = "내가 생성한 채팅방 목록을 조회합니다.")
    public ResponseEntity<List<ChatroomListResponseDto>> getMyCreatedChatrooms(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject ChatroomListRequestDto requestDto
    ) {
        return ResponseEntity.ok(chatroomService.getMyCreatedChatrooms(userDetails, requestDto));
    }
}
