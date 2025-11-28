package com.springboot.bizconnect.domain.user.repository;

import com.springboot.bizconnect.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}
