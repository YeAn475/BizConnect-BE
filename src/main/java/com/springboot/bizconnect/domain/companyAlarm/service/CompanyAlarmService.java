package com.springboot.bizconnect.domain.companyAlarm.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.companyAlarm.dto.approve.CompanyAlarmApproveResponseDto;
import com.springboot.bizconnect.domain.companyAlarm.dto.reject.CompanyAlarmRejectResponseDto;

public interface CompanyAlarmService {
    // 알림 수락
    CompanyAlarmApproveResponseDto approveAlarm(CustomUserDetails userDetails, Long alarmNo);
    // 알림 거절
    CompanyAlarmRejectResponseDto rejectAlarm(CustomUserDetails userDetails, Long alarmNo, String message);
}
