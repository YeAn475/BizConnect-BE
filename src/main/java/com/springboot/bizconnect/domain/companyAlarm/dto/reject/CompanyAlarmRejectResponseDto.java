package com.springboot.bizconnect.domain.companyAlarm.dto.reject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAlarmRejectResponseDto {
    private String message;
}
