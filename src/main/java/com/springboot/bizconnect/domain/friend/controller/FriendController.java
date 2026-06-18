package com.springboot.bizconnect.domain.friend.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.friend.dto.delete.FriendDeleteRequestDto;
import com.springboot.bizconnect.domain.friend.dto.delete.FriendDeleteResponseDto;
import com.springboot.bizconnect.domain.friend.dto.detail.FriendDetailRequestDto;
import com.springboot.bizconnect.domain.friend.dto.detail.FriendDetailResponseDto;
import com.springboot.bizconnect.domain.friend.dto.list.FriendListRequestDto;
import com.springboot.bizconnect.domain.friend.dto.list.FriendListResponseDto;
import com.springboot.bizconnect.domain.friend.dto.request.FriendRequestCancelResponseDto;
import com.springboot.bizconnect.domain.friend.dto.request.FriendRequestListRequestDto;
import com.springboot.bizconnect.domain.friend.dto.request.FriendRequestListResponseDto;
import com.springboot.bizconnect.domain.friend.dto.request.FriendRequestRequestDto;
import com.springboot.bizconnect.domain.friend.dto.request.FriendRequestResponseDto;
import com.springboot.bizconnect.domain.friend.dto.response.FriendResponseRequestDto;
import com.springboot.bizconnect.domain.friend.dto.response.FriendResponseResponseDto;
import com.springboot.bizconnect.domain.friend.dto.search.UserSearchRequestDto;
import com.springboot.bizconnect.domain.friend.dto.search.UserSearchResponseDto;
import com.springboot.bizconnect.domain.friend.service.FriendService;
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
@RequestMapping("/api/friend")
@Tag(name = "Friend", description = "친구 관련 API")
public class FriendController {

    private final FriendService friendService;

    // 유저 검색 (친구 추가용)
    @GetMapping("/search")
    @Operation(summary = "유저 검색", description = "친구 추가를 위해 유저를 검색합니다.")
    public ResponseEntity<List<UserSearchResponseDto>> searchUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject UserSearchRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.searchUser(userDetails, requestDto));
    }

    // 친구 요청
    @PostMapping("/request")
    @Operation(summary = "친구 요청", description = "원하는 유저에게 친구 요청을 보냅니다.")
    public ResponseEntity<FriendRequestResponseDto> requestFriend(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody FriendRequestRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.requestFriend(userDetails, requestDto));
    }

    // 친구 요청 응답 (수락/거절)
    @PutMapping("/response")
    @Operation(summary = "친구 요청 응답", description = "친구 요청을 수락/거절합니다.")
    public ResponseEntity<FriendResponseResponseDto> responseRequest(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody FriendResponseRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.responseRequest(userDetails, requestDto));
    }

    // 받은 친구 요청 목록
    @GetMapping("/request/received")
    @Operation(summary = "받은 친구 요청 목록", description = "받은 친구 요청 목록을 확인합니다.")
    public ResponseEntity<List<FriendRequestListResponseDto>> receivedRequestList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject FriendRequestListRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.receivedRequestList(userDetails, requestDto));
    }

    // 보낸 친구 요청 목록
    @GetMapping("/request/sent")
    @Operation(summary = "보낸 친구 요청 목록", description = "보낸 친구 요청 목록을 확인합니다.")
    public ResponseEntity<List<FriendRequestListResponseDto>> sentRequestList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject FriendRequestListRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.sentRequestList(userDetails, requestDto));
    }

    // 보낸 요청 취소
    @DeleteMapping("/request")
    @Operation(summary = "친구 요청 취소", description = "보낸 친구 요청을 취소합니다.")
    public ResponseEntity<FriendRequestCancelResponseDto> cancelRequest(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody FriendRequestRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.cancelRequest(userDetails, requestDto));
    }

    // 친구 목록
    @GetMapping("/list")
    @Operation(summary = "친구 목록", description = "친구 목록을 확인합니다.")
    public ResponseEntity<List<FriendListResponseDto>> friendList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject FriendListRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.friendList(userDetails, requestDto));
    }

    // 친구 정보
    @GetMapping("/detail")
    @Operation(summary = "친구 정보", description = "친구 정보를 확인합니다.")
    public ResponseEntity<FriendDetailResponseDto> friendDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject FriendDetailRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.friendDetail(userDetails, requestDto));
    }

    // 친구 삭제
    @DeleteMapping("")
    @Operation(summary = "친구 삭제", description = "친구를 삭제합니다.")
    public ResponseEntity<FriendDeleteResponseDto> friendDelete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody FriendDeleteRequestDto requestDto
    ) {
        return ResponseEntity.ok(friendService.friendDelete(userDetails, requestDto));
    }
}