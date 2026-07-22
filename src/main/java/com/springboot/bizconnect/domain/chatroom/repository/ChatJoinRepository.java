package com.springboot.bizconnect.domain.chatroom.repository;

import com.springboot.bizconnect.entity.ChatJoin;
import com.springboot.bizconnect.entity.Chatroom;
import com.springboot.bizconnect.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, ChatJoin.chatJoinNo> {

    boolean existsByUserAndChatroom(User user, Chatroom chatroom);

    Optional<ChatJoin> findByUserAndChatroom(User user, Chatroom chatroom);

    Page<ChatJoin> findByUser(User user, Pageable pageable);

    List<ChatJoin> findByChatroom(Chatroom chatroom);
}
