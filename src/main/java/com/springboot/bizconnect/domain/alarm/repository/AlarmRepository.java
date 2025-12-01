package com.springboot.bizconnect.domain.alarm.repository;

import com.springboot.bizconnect.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
