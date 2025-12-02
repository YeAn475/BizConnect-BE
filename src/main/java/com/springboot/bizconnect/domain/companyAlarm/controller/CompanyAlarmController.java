package com.springboot.bizconnect.domain.companyAlarm.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import com.springboot.bizconnect.domain.companyAlarm.dto.approve.CompanyAlarmApproveResponseDto;
import com.springboot.bizconnect.domain.companyAlarm.service.CompanyAlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companyAlarm")
@Tag(name = "Company Alarm", description = "회사 관련 알람 API")
public class CompanyAlarmController {

    private final CompanyAlarmService companyAlarmService;

    // 회사 요청 승인
    @PostMapping("/{requestId}/approve")
    @Operation(summary = "알람 수락", description = "관리자가 알람을 수락합니다.")
    public ResponseEntity<CompanyAlarmApproveResponseDto> approveAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject Long alarmNo
    ) {
        CompanyAlarmApproveResponseDto responseDto = companyAlarmService.approveAlarm(userDetails, alarmNo);
        return ResponseEntity.ok(responseDto);
    }

    // 회사 요청 거절
    @PutMapping("/{requestId}/reject")
    @Operation(summary = "알람 거절", description = "관리자가 알람을 거절합니다.")
    public ResponseEntity<Void> rejectAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject Long alarmNo
    ) {

        return null;
    }


    // 회사 삭제 승인
    // 회사 삭제 거절
}
