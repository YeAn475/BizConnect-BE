package com.springboot.bizconnect.domain.companyAlarm.dto.approve;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAlarmApproveResponseDto {
    private String CompanyName;
    private String affiliationName;
    private String branchName;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;
}
