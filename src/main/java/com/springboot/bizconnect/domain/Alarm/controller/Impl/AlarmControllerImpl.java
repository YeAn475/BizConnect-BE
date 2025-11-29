package com.springboot.bizconnect.domain.Alarm.controller.Impl;

import com.springboot.bizconnect.domain.Alarm.controller.AlarmController;
import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmRequestDto;
import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmResponseDto;
import com.springboot.bizconnect.domain.Alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarm")
@Tag(name = "Alarm", description = "알람 관련 API")
@RequiredArgsConstructor
public class AlarmControllerImpl implements AlarmController {

    private final AlarmService alarmService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "알림 생성", description = "유저가 알림을 생성합니다.")

    public ResponseEntity<CreateAlarmResponseDto> createAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CreateAlarmRequestDto requestDto
    ) {
        CreateAlarmResponseDto responseDto = alarmService.createAlarm(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
