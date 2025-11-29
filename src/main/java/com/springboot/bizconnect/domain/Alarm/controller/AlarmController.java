package com.springboot.bizconnect.domain.Alarm.controller;

import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmRequestDto;
import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmResponseDto;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface AlarmController {
    // 알람 생성
    ResponseEntity<CreateAlarmResponseDto> createAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CreateAlarmRequestDto requestDto
            );
//    // 알람 리스트 조회
//    ResponseEntity<?> getAlarmList(@AuthenticationPrincipal CustomUserDetails userDetails);
//    // 알람 읽기
//    ResponseEntity<?> readAlarm(@AuthenticationPrincipal CustomUserDetails userDetails);
//    // 알람 삭제
//    ResponseEntity<?> deleteAlarm(@AuthenticationPrincipal CustomUserDetails userDetails);
}
