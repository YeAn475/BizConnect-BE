package com.springboot.bizconnect.domain.alarm.repository;

import com.springboot.bizconnect.entity.UserAlarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, UserAlarm.userAlarmNo> {
    Page<UserAlarm> findByUser_UserNoAndIsDeletedFalse(Long userNo, Pageable pageable);
    Optional<UserAlarm> findByUser_UserNoAndAlarm_AlarmNo(Long userNo, Long alarmNo);
}
