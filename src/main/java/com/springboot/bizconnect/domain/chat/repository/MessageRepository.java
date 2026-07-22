package com.springboot.bizconnect.domain.chat.repository;

import com.springboot.bizconnect.entity.Chatroom;
import com.springboot.bizconnect.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChatroomOrderByCreatedAtAsc(Chatroom chatroom, Pageable pageable);
}
