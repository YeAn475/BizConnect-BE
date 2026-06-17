package com.springboot.bizconnect.domain.friend.service;

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
import com.springboot.bizconnect.domain.friend.dto.search.FriendSearchRequestDto;
import com.springboot.bizconnect.domain.friend.dto.search.FriendSearchResponseDto;
import com.springboot.bizconnect.domain.friend.dto.search.UserSearchRequestDto;
import com.springboot.bizconnect.domain.friend.dto.search.UserSearchResponseDto;

import java.util.List;

public interface FriendService {
    // 검색
    List<UserSearchResponseDto> searchUser(CustomUserDetails userDetails, UserSearchRequestDto requestDto);
    List<FriendSearchResponseDto> searchFriend(CustomUserDetails userDetails, FriendSearchRequestDto requestDto);

    // 친구 요청
    FriendRequestResponseDto requestFriend(CustomUserDetails userDetails, FriendRequestRequestDto requestDto);
    FriendResponseResponseDto responseRequest(CustomUserDetails userDetails, FriendResponseRequestDto requestDto);
    List<FriendRequestListResponseDto> receivedRequestList(CustomUserDetails userDetails, FriendRequestListRequestDto requestDto);
    List<FriendRequestListResponseDto> sentRequestList(CustomUserDetails userDetails, FriendRequestListRequestDto requestDto);
    FriendRequestCancelResponseDto cancelRequest(CustomUserDetails userDetails, FriendRequestRequestDto requestDto);

    // 친구 관리
    List<FriendListResponseDto> friendList(CustomUserDetails userDetails, FriendListRequestDto requestDto);
    FriendDetailResponseDto friendDetail(CustomUserDetails userDetails, FriendDetailRequestDto requestDto);
    FriendDeleteResponseDto friendDelete(CustomUserDetails userDetails, FriendDeleteRequestDto requestDto);
}