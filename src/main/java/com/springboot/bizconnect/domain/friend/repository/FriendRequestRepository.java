package com.springboot.bizconnect.domain.friend.repository;

import com.springboot.bizconnect.entity.FriendRequest;
import com.springboot.bizconnect.enums.friendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    // 받은 요청 목록
    Page<FriendRequest> findByReceiver_UserNoAndStatus(Long receiverUserNo, friendStatus status, Pageable pageable);

    // 보낸 요청 목록
    Page<FriendRequest> findBySender_UserNoAndStatus(Long senderUserNo, friendStatus status, Pageable pageable);

    // 특정 요청 조회
    Optional<FriendRequest> findBySender_UserNoAndReceiver_UserNo(Long senderUserNo, Long receiverUserNo);

    // 이미 요청 존재 확인 (양방향)
    boolean existsBySender_UserNoAndReceiver_UserNoAndStatus(Long senderUserNo, Long receiverUserNo, friendStatus status);
}