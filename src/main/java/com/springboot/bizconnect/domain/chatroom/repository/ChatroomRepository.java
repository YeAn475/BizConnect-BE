package com.springboot.bizconnect.domain.chatroom.repository;

import com.springboot.bizconnect.entity.Chatroom;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.chatroomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Page<Chatroom> findByStatusNot(chatroomStatus status, Pageable pageable);

    Page<Chatroom> findByCreatedByAndStatusNot(User createdBy, chatroomStatus status, Pageable pageable);
}
