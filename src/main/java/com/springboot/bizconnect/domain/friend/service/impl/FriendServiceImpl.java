package com.springboot.bizconnect.domain.friend.service.impl;

import com.springboot.bizconnect.domain.alarm.service.AlarmService;
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
import com.springboot.bizconnect.domain.friend.repository.FriendRequestRepository;
import com.springboot.bizconnect.domain.friend.repository.FriendshipRepository;
import com.springboot.bizconnect.domain.friend.service.FriendService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.FriendRequest;
import com.springboot.bizconnect.entity.Friendship;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.AlarmType;
import com.springboot.bizconnect.enums.friendStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;
    private final AlarmService alarmService;

    // ==================== 검색 ====================

    @Override
    public List<UserSearchResponseDto> searchUser(CustomUserDetails userDetails, UserSearchRequestDto requestDto) {
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        Page<User> userPage = userRepository.findByNameContainingOrEmailContaining(
                requestDto.getKeyword(), requestDto.getKeyword(), pageRequest);

        return userPage.getContent().stream()
                .map(user -> UserSearchResponseDto.builder()
                        .userNo(user.getUserNo())
                        .name(user.getName())
                        .email(user.getEmail())
                        .companyName(user.getCompany() != null ? user.getCompany().getName() : null)
                        .imageUrl(user.getImageUrl())
                        .isOpen(user.getIsOpen())
                        .build())
                .toList();
    }

    @Override
    public List<FriendSearchResponseDto> searchFriend(CustomUserDetails userDetails, FriendSearchRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return friendshipRepository.findByUserNoAndFriendNameContaining(userNo, requestDto.getKeyword(), pageRequest)
                .map(friendship -> FriendSearchResponseDto.builder()
                        .userNo(friendship.getFriend().getUserNo())
                        .name(friendship.getFriend().getName())
                        .companyName(friendship.getFriend().getCompany() != null ?
                                friendship.getFriend().getCompany().getName() : null)
                        .imageUrl(friendship.getFriend().getImageUrl())
                        .friendSince(friendship.getCreatedAt())
                        .build())
                .getContent();
    }

    // ==================== 친구 요청 ====================

    @Override
    public FriendRequestResponseDto requestFriend(CustomUserDetails userDetails, FriendRequestRequestDto requestDto) {
        User sender = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        User receiver = userRepository.findById(requestDto.getReceiverUserNo())
                .orElseThrow(() -> new RuntimeException("대상 유저를 찾을 수 없습니다."));

        // 자기 자신에게 요청 불가
        if (sender.getUserNo().equals(receiver.getUserNo())) {
            throw new RuntimeException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        // 이미 친구인지 확인
        if (friendshipRepository.existsByUser_UserNoAndFriend_UserNo(sender.getUserNo(), receiver.getUserNo())) {
            throw new RuntimeException("이미 친구입니다.");
        }

        // 이미 요청했는지 확인
        if (friendRequestRepository.existsBySender_UserNoAndReceiver_UserNoAndStatus(
                sender.getUserNo(), receiver.getUserNo(), friendStatus.PENDING)) {
            throw new RuntimeException("이미 친구 요청을 보냈습니다.");
        }

        // 상대방이 나에게 요청했는지 확인
        if (friendRequestRepository.existsBySender_UserNoAndReceiver_UserNoAndStatus(
                receiver.getUserNo(), sender.getUserNo(), friendStatus.PENDING)) {
            throw new RuntimeException("상대방이 이미 친구 요청을 보냈습니다. 받은 요청을 확인해주세요.");
        }

        // 친구 요청 생성
        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(friendStatus.PENDING)
                .build();

        friendRequestRepository.save(friendRequest);

        // 알람 전송
        alarmService.sendToUser(
                sender,
                receiver.getUserNo(),
                "친구 요청",
                sender.getName() + "님이 친구 요청을 보냈습니다.",
                AlarmType.FRIEND_REQUEST,
                Long.valueOf(friendRequest.getFriendRequestNo())
        );

        return FriendRequestResponseDto.builder()
                .message("친구 요청을 보냈습니다.")
                .build();
    }

    @Override
    public FriendResponseResponseDto responseRequest(CustomUserDetails userDetails, FriendResponseRequestDto requestDto) {
        User receiver = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        FriendRequest friendRequest = friendRequestRepository.findById(requestDto.getRequestNo().intValue())
                .orElseThrow(() -> new RuntimeException("친구 요청을 찾을 수 없습니다."));

        // 본인에게 온 요청인지 확인
        if (!friendRequest.getReceiver().getUserNo().equals(receiver.getUserNo())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 이미 처리된 요청인지 확인
        if (friendRequest.getStatus() != friendStatus.PENDING) {
            throw new RuntimeException("이미 처리된 요청입니다.");
        }

        friendStatus newStatus;
        String message;
        try {
            newStatus = friendStatus.valueOf(requestDto.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 상태값입니다. (ACCEPTED 또는 REJECTED)");
        }

        if (newStatus == friendStatus.ACCEPTED) {
            // 친구 관계 생성 (양방향)
            Friendship friendship1 = Friendship.builder()
                    .user(receiver)
                    .friend(friendRequest.getSender())
                    .createdAt(LocalDateTime.now())
                    .build();

            Friendship friendship2 = Friendship.builder()
                    .user(friendRequest.getSender())
                    .friend(receiver)
                    .createdAt(LocalDateTime.now())
                    .build();

            friendshipRepository.save(friendship1);
            friendshipRepository.save(friendship2);

            message = "친구 요청을 수락했습니다.";

            // 알람 전송
            alarmService.sendToUser(
                    receiver,
                    friendRequest.getSender().getUserNo(),
                    "친구 요청 수락",
                    receiver.getName() + "님이 친구 요청을 수락했습니다.",
                    AlarmType.GENERAL,
                    null
            );
        } else {
            message = "친구 요청을 거절했습니다.";

            // 알람 전송
            alarmService.sendToUser(
                    receiver,
                    friendRequest.getSender().getUserNo(),
                    "친구 요청 거절",
                    receiver.getName() + "님이 친구 요청을 거절했습니다.",
                    AlarmType.GENERAL,
                    null
            );
        }

        friendRequest.setStatus(newStatus);
        friendRequestRepository.save(friendRequest);

        return FriendResponseResponseDto.builder()
                .message(message)
                .build();
    }

    @Override
    public List<FriendRequestListResponseDto> receivedRequestList(CustomUserDetails userDetails, FriendRequestListRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return friendRequestRepository.findByReceiver_UserNoAndStatus(userNo, friendStatus.PENDING, pageRequest)
                .map(request -> FriendRequestListResponseDto.builder()
                        .requestNo(Long.valueOf(request.getFriendRequestNo()))
                        .userNo(request.getSender().getUserNo())
                        .name(request.getSender().getName())
                        .companyName(request.getSender().getCompany() != null ?
                                request.getSender().getCompany().getName() : null)
                        .imageUrl(request.getSender().getImageUrl())
                        .status(request.getStatus().name())
                        .createdAt(request.getCreatedAt())
                        .build())
                .getContent();
    }

    @Override
    public List<FriendRequestListResponseDto> sentRequestList(CustomUserDetails userDetails, FriendRequestListRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return friendRequestRepository.findBySender_UserNoAndStatus(userNo, friendStatus.PENDING, pageRequest)
                .map(request -> FriendRequestListResponseDto.builder()
                        .requestNo(Long.valueOf(request.getFriendRequestNo()))
                        .userNo(request.getReceiver().getUserNo())
                        .name(request.getReceiver().getName())
                        .companyName(request.getReceiver().getCompany() != null ?
                                request.getReceiver().getCompany().getName() : null)
                        .imageUrl(request.getReceiver().getImageUrl())
                        .status(request.getStatus().name())
                        .createdAt(request.getCreatedAt())
                        .build())
                .getContent();
    }

    @Override
    public FriendRequestCancelResponseDto cancelRequest(CustomUserDetails userDetails, FriendRequestRequestDto requestDto) {
        Long senderNo = userDetails.getUser().getUserNo();

        FriendRequest friendRequest = friendRequestRepository.findBySender_UserNoAndReceiver_UserNo(
                        senderNo, requestDto.getReceiverUserNo())
                .orElseThrow(() -> new RuntimeException("친구 요청을 찾을 수 없습니다."));

        if (friendRequest.getStatus() != friendStatus.PENDING) {
            throw new RuntimeException("이미 처리된 요청은 취소할 수 없습니다.");
        }

        friendRequestRepository.delete(friendRequest);

        return FriendRequestCancelResponseDto.builder()
                .message("친구 요청을 취소했습니다.")
                .build();
    }

    // ==================== 친구 관리 ====================

    @Override
    public List<FriendListResponseDto> friendList(CustomUserDetails userDetails, FriendListRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return friendshipRepository.findByUser_UserNo(userNo, pageRequest)
                .map(friendship -> FriendListResponseDto.builder()
                        .userNo(friendship.getFriend().getUserNo())
                        .name(friendship.getFriend().getName())
                        .companyName(friendship.getFriend().getCompany() != null ?
                                friendship.getFriend().getCompany().getName() : null)
                        .imageUrl(friendship.getFriend().getImageUrl())
                        .friendSince(friendship.getCreatedAt())
                        .build())
                .getContent();
    }

    @Override
    public FriendDetailResponseDto friendDetail(CustomUserDetails userDetails, FriendDetailRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();

        // 친구 관계 확인
        Friendship friendship = friendshipRepository.findByUser_UserNoAndFriend_UserNo(userNo, requestDto.getUserNo())
                .orElseThrow(() -> new RuntimeException("친구가 아닙니다."));

        User friend = friendship.getFriend();

        // isOpen 확인
        if (!friend.getIsOpen()) {
            return FriendDetailResponseDto.builder()
                    .userNo(friend.getUserNo())
                    .name(friend.getName())
                    .imageUrl(friend.getImageUrl())
                    .message("비공개 프로필입니다.")
                    .build();
        }

        return FriendDetailResponseDto.builder()
                .userNo(friend.getUserNo())
                .name(friend.getName())
                .email(friend.getEmail())
                .phoneNumber(friend.getPhoneNumber())
                .companyName(friend.getCompany() != null ? friend.getCompany().getName() : null)
                .imageUrl(friend.getImageUrl())
                .friendSince(friendship.getCreatedAt())
                .build();
    }

    @Override
    public FriendDeleteResponseDto friendDelete(CustomUserDetails userDetails, FriendDeleteRequestDto requestDto) {
        Long userNo = userDetails.getUser().getUserNo();

        // 양방향 친구 관계 삭제
        Friendship friendship1 = friendshipRepository.findByUser_UserNoAndFriend_UserNo(userNo, requestDto.getFriendUserNo())
                .orElseThrow(() -> new RuntimeException("친구가 아닙니다."));

        Friendship friendship2 = friendshipRepository.findByUser_UserNoAndFriend_UserNo(requestDto.getFriendUserNo(), userNo)
                .orElse(null);

        friendshipRepository.delete(friendship1);
        if (friendship2 != null) {
            friendshipRepository.delete(friendship2);
        }

        return FriendDeleteResponseDto.builder()
                .message("친구를 삭제했습니다.")
                .build();
    }
}