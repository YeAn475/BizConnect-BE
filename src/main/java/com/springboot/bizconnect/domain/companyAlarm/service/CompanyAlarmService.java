package com.springboot.bizconnect.domain.companyAlarm.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.companyAlarm.dto.approve.CompanyAlarmApproveResponseDto;

public interface CompanyAlarmService {
    // 알림 수락
    CompanyAlarmApproveResponseDto approveAlarm(CustomUserDetails userDetails, Long alarmNo);
    // 알림 거절
    void rejectAlarm(CustomUserDetails userDetails, Long alarmNo);
}
