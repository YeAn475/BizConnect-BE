package com.springboot.bizconnect.domain.friend.repository;

import com.springboot.bizconnect.entity.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    // 친구 목록
    Page<Friendship> findByUser_UserNo(Long userNo, Pageable pageable);

    // 친구 관계 확인
    boolean existsByUser_UserNoAndFriend_UserNo(Long userNo, Long friendNo);

    // 친구 관계 조회
    Optional<Friendship> findByUser_UserNoAndFriend_UserNo(Long userNo, Long friendNo);

    // 친구 목록에서 이름 검색
    @Query("SELECT f FROM Friendship f WHERE f.user.userNo = :userNo AND f.friend.name LIKE %:keyword%")
    Page<Friendship> findByUserNoAndFriendNameContaining(@Param("userNo") Long userNo, @Param("keyword") String keyword, Pageable pageable);
}