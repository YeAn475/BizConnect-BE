package com.springboot.bizconnect.domain.Alarm.repository;

import com.springboot.bizconnect.entity.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
}
